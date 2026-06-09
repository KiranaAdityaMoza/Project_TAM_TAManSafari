package com.example.uts_tam_tamansafari.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_tam_tamansafari.data.model.RegisterRequest
import com.example.uts_tam_tamansafari.data.repository.Repository
import com.example.uts_tam_tamansafari.data.repository.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel : ViewModel() {
    private val repository = Repository()

    var uiState: RegisterUiState by mutableStateOf(RegisterUiState.Idle)
        private set

    fun register(username: String, email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            val request = RegisterRequest(username, email, password, firstName, lastName)
            repository.register(request).collectLatest { resource ->
                uiState = when (resource) {
                    is Resource.Loading -> RegisterUiState.Loading
                    is Resource.Success -> RegisterUiState.Success
                    is Resource.Error -> RegisterUiState.Error(resource.message)
                }
            }
        }
    }

    fun resetState() {
        uiState = RegisterUiState.Idle
    }
}
