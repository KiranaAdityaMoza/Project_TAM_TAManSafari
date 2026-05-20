package com.example.uts_tam_tamansafari.data.remote.api

import com.example.uts_tam_tamansafari.data.model.request.LoginRequest
import com.example.uts_tam_tamansafari.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}