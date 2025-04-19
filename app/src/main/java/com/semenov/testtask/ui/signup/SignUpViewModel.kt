package com.semenov.testtask.ui.signup

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenov.domain.signuprepository.SignUpRepository
import com.semenov.domain.signuprepository.model.RegisterResponseState
import com.semenov.domain.signuprepository.model.RegisterUser
import com.semenov.testtask.model.AppPosition
import com.semenov.testtask.model.mapToApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository,
) : ViewModel() {
    var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.InitialLoading)
        private set
    private var token: String? = null

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                token = repository.getToken()
                val positions = repository.getPositions().mapToApp().positions

                uiState = when (val current = uiState) {
                    is RegisterUiState.Form -> current.copy(positions = positions)
                    else -> RegisterUiState.Form(positions = positions)
                }

            } catch (e: Exception) {
                uiState = RegisterUiState.Error("Failed to load initial data")
            }
        }
    }

    fun onFieldChange(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        positionId: Int? = null,
        photoFile: File? = null
    ) {
        val current = uiState as? RegisterUiState.Form ?: return
        uiState = current.copy(
            name = name ?: current.name,
            email = email ?: current.email,
            phone = phone ?: current.phone,
            positionId = positionId ?: current.positionId,
            photoFile = photoFile ?: current.photoFile
        )
    }

    fun onSubmit() {
        val form = uiState as? RegisterUiState.Form ?: return
        val errors = validateForm(form)
        if (errors?.hasErrors() == true) {
            uiState = form.copy(errors = errors)
            return
        }

        uiState = RegisterUiState.InitialLoading

        viewModelScope.launch {
            val result = repository.registerUser(
                token = token!!,
                user = RegisterUser(
                    name = form.name,
                    email = form.email,
                    phone = form.phone,
                    positionId = form.positionId!!,
                    photo = form.photoFile!!
                )
            )
            uiState = when (result) {
                is RegisterResponseState.ErrorToken -> RegisterUiState.Error(result.message)
                is RegisterResponseState.ErrorNameOrEmail -> RegisterUiState.Error(result.message)
                is RegisterResponseState.ErrorRegisterForm -> RegisterUiState.Form(errors = result.mapErrors.toFormErrors())
                is RegisterResponseState.UnknownError -> RegisterUiState.Error(message = result.message)
                is RegisterResponseState.Success -> RegisterUiState.Success(result.userId, result.message)
            }
        }
    }

    private fun validateForm(form: RegisterUiState.Form): FormErrors? {
        val errors = FormErrors(
            name = if (form.name.length < 2) "Required field" else null,
            email = if (!Patterns.EMAIL_ADDRESS.matcher(form.email).matches()) "Invalid email format" else null,
            phone = if (!form.phone.matches(Regex("^[+]?380[0-9]{9}$"))) "Required field" else null,
            photo = if (form.photoFile == null) "Photo is required" else null,
            position = if (form.positionId == null) "Choose position" else null,
        )
        return if (errors.hasErrors()) errors else null
    }

    fun resetUiState() {
        uiState = RegisterUiState.Form()
    }
}

sealed class RegisterUiState {
    object InitialLoading : RegisterUiState()
    data class Success(val userId: Int = 0, val message: String = "") : RegisterUiState()
    data class Error(val message: String = "") : RegisterUiState()
    data class Form(
        val name: String = "",
        val email: String = "",
        val phone: String = "",
        val positionId: Int? = null,
        val photoFile: File? = null,
        val positions: List<AppPosition> = emptyList(),
        val errors: FormErrors? = null
    ) : RegisterUiState()
}

data class FormErrors(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val position: String? = null,
    val photo: String? = null
) {
    fun hasErrors(): Boolean =
        listOf(name, email, phone, position, photo).any { it != null }
}

fun Map<String, List<String>>.toFormErrors(): FormErrors {
    return FormErrors(
        name = this["name"]?.firstOrNull(),
        email = this["email"]?.firstOrNull(),
        phone = this["phone"]?.firstOrNull(),
        position = this["position_id"]?.firstOrNull(),
        photo = this["photo"]?.firstOrNull()
    )
}