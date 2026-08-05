package com.corevo.main.data.network

import com.corevo.main.data.model.AuthResponse
import com.corevo.main.data.model.LoginRequest
import com.corevo.main.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
