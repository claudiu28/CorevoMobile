package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corevo.main.AppContainer

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(appContainer.authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(appContainer.socialRepository) as T
            }
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> {
                LibraryViewModel(appContainer.workoutRepository) as T
            }
            modelClass.isAssignableFrom(WorkoutsViewModel::class.java) -> {
                WorkoutsViewModel(appContainer.workoutRepository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(appContainer.socialRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(appContainer.profileRepository, appContainer.authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
