package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.data.model.ActivityHeatmap
import com.corevo.main.data.model.UserDetails
import com.corevo.main.repo.AuthRepository
import com.corevo.main.repo.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private val _heatmap = MutableStateFlow(ActivityHeatmap())
    val heatmap: StateFlow<ActivityHeatmap> = _heatmap

    val isLoading = MutableStateFlow(false)

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            isLoading.value = true
            val pRes = profileRepository.getMyProfile()
            if (pRes.isSuccess) _userDetails.value = pRes.getOrNull()

            val hRes = profileRepository.getActivityHeatmap()
            if (hRes.isSuccess) _heatmap.value = hRes.getOrNull() ?: ActivityHeatmap()
            isLoading.value = false
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogout()
        }
    }
}
