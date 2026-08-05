package com.corevo.main.repo

import com.corevo.main.data.model.AuthResponse
import com.corevo.main.data.model.LoginRequest
import com.corevo.main.data.model.RegisterRequest
import com.corevo.main.data.network.AuthApi
import com.corevo.main.system.SessionManager

class AuthRepository(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
) {
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                sessionManager.saveSession(body.access_token, body.user?.username ?: email, email)
                Result.success(body)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, email: String, pass: String, confirm: String): Result<AuthResponse> {
        return try {
            val response = authApi.register(RegisterRequest(username, email, pass, confirm))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                sessionManager.saveSession(body.access_token, username, email)
                Result.success(body)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        sessionManager.clearSession()
    }

    suspend fun isLoggedIn(): Boolean {
        return !sessionManager.getToken().isNullOrEmpty()
    }
}
