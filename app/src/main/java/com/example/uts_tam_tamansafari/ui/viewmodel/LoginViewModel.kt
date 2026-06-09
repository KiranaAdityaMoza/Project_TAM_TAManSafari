package com.example.uts_tam_tamansafari.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.repository.Repository
import com.example.uts_tam_tamansafari.data.repository.Resource
import com.example.uts_tam_tamansafari.ui.view.LoginUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = Repository()

    var uiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.login(LoginRequest(username.trim(), password.trim())).collectLatest { resource ->
                uiState = when (resource) {
                    is Resource.Loading -> LoginUiState.Loading
                    is Resource.Success -> {
                        LoginUiState.Success(
                            token = resource.data.token,
                            username = resource.data.username,
                            firstName = resource.data.firstName,
                            lastName = resource.data.lastName
                        )
                    }
                    is Resource.Error -> LoginUiState.Error(resource.message)
                }
            }
        }
    }

    fun resetState() {
        uiState = LoginUiState.Idle
    }
}
