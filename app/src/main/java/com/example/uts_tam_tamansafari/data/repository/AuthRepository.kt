package com.example.uts_tam_tamansafari.data.repository

import com.example.uts_tam_tamansafari.data.model.request.LoginRequest
import com.example.uts_tam_tamansafari.data.model.response.LoginResponse
import com.example.uts_tam_tamansafari.data.remote.retrofit.RetrofitClient

class AuthRepository {
    private val api = RetrofitClient.authApi

    suspend fun login(username: String, password: String): LoginResponse {
        return api.login(LoginRequest(username, password))
    }
}