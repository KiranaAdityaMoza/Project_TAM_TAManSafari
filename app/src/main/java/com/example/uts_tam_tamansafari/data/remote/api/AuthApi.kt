package com.example.uts_tam_tamansafari.data.remote.api

import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}