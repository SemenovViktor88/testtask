package com.semenov.data.signup

import com.semenov.data.signup.api.SignUpApi
import com.semenov.data.signup.model.mapToDomain
import com.semenov.domain.signuprepository.SignUpRepository
import com.semenov.domain.signuprepository.model.RegisterResponseState
import com.semenov.domain.signuprepository.model.RegisterUser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api: SignUpApi
): SignUpRepository {

    override var cachedToken: String? = null

    override suspend fun getToken(): String {
        if (cachedToken != null) return cachedToken!!
        val response = api.getToken()
        if (response.success) {
            cachedToken = response.token
            return response.token
        } else throw Exception("Token fetch failed")
    }

    override suspend fun getPositions() = api.getPosition().mapToDomain()

    override suspend fun registerUser(token: String, user: RegisterUser): RegisterResponseState {
        val namePart = user.name.toRequestBody("text/plain".toMediaType())
        val emailPart = user.email.toRequestBody("text/plain".toMediaType())
        val phonePart = user.phone.toRequestBody("text/plain".toMediaType())
        val positionIdPart = user.positionId.toString().toRequestBody("text/plain".toMediaType())
        val photoRequest = user.photo.asRequestBody("image/jpeg".toMediaType())
        val photoPart = MultipartBody.Part.createFormData(
            name = "photo",
            filename = user.photo.name,
            body = photoRequest
        )

        return try {
            val response = api.registerUser(
                token = token,
                name = namePart,
                email = emailPart,
                phone = phonePart,
                positionId = positionIdPart,
                photo = photoPart
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    RegisterResponseState.Success(body.success, body.userId, body.message)
                } else {
                    RegisterResponseState.UnknownError(body?.message ?: "Unknown error")
                }
            } else {
                val code = response.code()
                val errorJson = response.errorBody()?.string()

                when (code) {
                    401 -> {
                        val msg = parseMessage(errorJson)
                        RegisterResponseState.ErrorToken(message = msg ?: "Token expired")
                    }

                    409 -> {
                        val msg = parseMessage(errorJson)
                        RegisterResponseState.ErrorNameOrEmail(
                            message = msg ?: "User already exists"
                        )
                    }

                    422 -> {
                        val (msg, errors) = parse422Fails(errorJson)
                        RegisterResponseState.ErrorRegisterForm(msg, errors)
                    }

                    else -> {
                        RegisterResponseState.UnknownError("Http $code: ${parseMessage(errorJson) ?: "Unknown"}")
                    }
                }
            }
        } catch (e: IOException) {
            RegisterResponseState.UnknownError("Network error: ${e.localizedMessage}")
        } catch (e: Exception) {
            RegisterResponseState.UnknownError("Unexpected error: ${e.localizedMessage}")
        }
    }

    private fun parseMessage(json: String?): String? {
        return runCatching {
            JSONObject(json ?: return null).optString("message")
        }.getOrNull()
    }

    private fun parse422Fails(json: String?): Pair<String, Map<String, List<String>>> {
        return try {
            val obj = JSONObject(json ?: return "" to emptyMap())
            val message = obj.optString("message")
            val fails = obj.optJSONObject("fails") ?: return message to emptyMap()

            val map = mutableMapOf<String, List<String>>()
            fails.keys().forEach { key ->
                val errors = fails.getJSONArray(key)
                map[key] = List(errors.length()) { i -> errors.getString(i) }
            }
            message to map
        } catch (e: Exception) {
            "Invalid 422 response" to emptyMap()
        }
    }
}