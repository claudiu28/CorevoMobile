package com.corevo.main.data.model

data class AuthResponse(
    val message: String? = null,
    val email: String? = null,
    val access_token: String? = null,
    val user: UserEssentials? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
