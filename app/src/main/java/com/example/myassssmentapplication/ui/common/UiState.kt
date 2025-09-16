package com.example.myassssmentapplication.ui.common

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val keypass: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

sealed class DashboardUiState {
    object Idle : DashboardUiState()
    object Loading : DashboardUiState()
    data class Success(val entities: List<com.example.myassssmentapplication.domain.model.FitnessEntity>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
