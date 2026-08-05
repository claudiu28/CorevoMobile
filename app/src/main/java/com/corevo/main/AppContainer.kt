package com.corevo.main

import android.content.Context
import com.corevo.main.data.network.NetworkModule
import com.corevo.main.realtime.RealtimeService
import com.corevo.main.repo.*
import com.corevo.main.system.NetworkStatusProvider
import com.corevo.main.system.SessionManager

class AppContainer(private val context: Context) {
    val sessionManager: SessionManager by lazy { SessionManager(context) }
    val networkStatusProvider: NetworkStatusProvider by lazy { NetworkStatusProvider(context) }
    
    val networkModule: NetworkModule by lazy { NetworkModule(sessionManager) }
    val realtimeService: RealtimeService by lazy { RealtimeService(sessionManager) }

    val authRepository: AuthRepository by lazy {
        AuthRepository(networkModule.authApi, sessionManager)
    }
    
    val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(networkModule.workoutApi)
    }

    val socialRepository: SocialRepository by lazy {
        SocialRepository(networkModule.socialApi, networkModule.chatApi)
    }

    val profileRepository: ProfileRepository by lazy {
        ProfileRepository(networkModule.profileApi)
    }
}
