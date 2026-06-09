package com.example.uts_tam_tamansafari.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_tam_tamansafari.data.repository.Repository
import com.example.uts_tam_tamansafari.ui.view.LoginUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val repository = Repository()

    var uiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            try {
                val response = repository.login(username.trim(), password.trim())
                uiState = LoginUiState.Success(response.token)
            } catch (e: HttpException) {
                uiState = LoginUiState.Error("Username atau password salah (HTTP ${e.code()})")
            } catch (e: Exception) {
                uiState = LoginUiState.Error(e.localizedMessage ?: "Terjadi kesalahan koneksi")
            }
        }
    }
}
