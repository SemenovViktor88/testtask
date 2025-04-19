package com.semenov.testtask.ui.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenov.domain.usersrepository.UsersRepository
import com.semenov.testtask.model.AppPageUser
import com.semenov.testtask.model.mapToApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AppPageUser.initial())
    val state: StateFlow<AppPageUser> = _state

    private val _isInitialLoading = MutableStateFlow(false)
    val isInitialLoading: StateFlow<Boolean> = _isInitialLoading

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating

    init {
        loadUsers()
    }

    fun loadUsers() {
        val currentPage = _state.value.page
        val totalPages = _state.value.totalPages
        if (_isPaginating.value  || currentPage >= totalPages && totalPages != 0) return

        viewModelScope.launch {
            _isPaginating.value = true
            _isInitialLoading.value = true
            try {
                val response = usersRepository.getUsers(page = currentPage + 1, count = 6).mapToApp()

                _state.update { oldState ->
                    oldState.copy(
                        success = response.success,
                        page = response.page,
                        totalPages = response.totalPages,
                        totalUsers = response.totalUsers,
                        count = response.count,
                        links = response.links,
                        users = oldState.users + response.users
                    )
                }

            } catch (e: Exception) {
                Log.e("UsersViewModel", "Error loading users", e)
            } finally {
                _isInitialLoading.value = false
                _isPaginating.value  = false
            }
        }
    }
}
