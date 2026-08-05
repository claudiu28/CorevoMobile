package com.corevo.main.data.network

import com.corevo.main.system.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = runBlocking { sessionManager.getToken() }
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        requestBuilder.addHeader("Accept", "application/json")
        val response = chain.proceed(requestBuilder.build())
        if (response.code == 401 || response.code == 403) {
            runBlocking { sessionManager.clearSession() }
        }
        return response
    }
}
