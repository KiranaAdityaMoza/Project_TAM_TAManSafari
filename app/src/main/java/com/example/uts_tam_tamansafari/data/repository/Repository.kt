package com.example.uts_tam_tamansafari.data.repository

import com.example.uts_tam_tamansafari.data.ApiService
import com.example.uts_tam_tamansafari.data.RetrofitClient
import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.model.LoginResponse
import com.example.uts_tam_tamansafari.data.model.RegisterRequest
import com.example.uts_tam_tamansafari.data.model.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class Repository {
    private val api = RetrofitClient.instance

    fun login(request: LoginRequest) = flow {
        emit(Resource.Loading)
        try {
            val response = api.login(request)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    fun register(request: RegisterRequest) = flow {
        emit(Resource.Loading)
        try {
            val response = api.register(request)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

}
