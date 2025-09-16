package com.example.myassssmentapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.ui.common.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Please enter both username and password")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            // Simulate login - for demo purposes, accept any non-empty credentials
            kotlinx.coroutines.delay(1000) // Simulate network delay
            if (username.isNotBlank() && password.isNotBlank()) {
                _uiState.value = LoginUiState.Success("fitness")
            } else {
                _uiState.value = LoginUiState.Error("Login failed. Please try again.")
            }
        }
    }

    fun clearError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }
}
