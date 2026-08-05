package com.corevo.main.data.network

import com.corevo.main.system.SessionManager
import com.corevo.main.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule(private val sessionManager: SessionManager) {
    private val okHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.DEFAULT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val workoutApi: WorkoutApi by lazy { retrofit.create(WorkoutApi::class.java) }
    val socialApi: SocialApi by lazy { retrofit.create(SocialApi::class.java) }
    val profileApi: ProfileApi by lazy { retrofit.create(ProfileApi::class.java) }
    val chatApi: ChatApi by lazy { retrofit.create(ChatApi::class.java) }
}
