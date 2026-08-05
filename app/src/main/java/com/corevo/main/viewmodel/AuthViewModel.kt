package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.repo.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val username = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val isLoginMode = MutableStateFlow(true)

    fun switchMode() {
        isLoginMode.value = !isLoginMode.value
        _authState.value = AuthState.Idle
    }

    fun submit() {
        if (email.value.isBlank() || password.value.isBlank()) {
            _authState.value = AuthState.Error("Please fill in all fields")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            if (isLoginMode.value) {
                val res = authRepository.login(email.value, password.value)
                if (res.isSuccess) _authState.value = AuthState.Success
                else _authState.value = AuthState.Error(res.exceptionOrNull()?.message ?: "Login failed")
            } else {
                if (username.value.isBlank() || confirmPassword.value != password.value) {
                    _authState.value = AuthState.Error("Passwords don't match or invalid username")
                    return@launch
                }
                val res = authRepository.register(username.value, email.value, password.value, confirmPassword.value)
                if (res.isSuccess) _authState.value = AuthState.Success
                else _authState.value = AuthState.Error(res.exceptionOrNull()?.message ?: "Register failed")
            }
        }
    }
}
