package com.example.uts_tam_tamansafari.data

import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.model.LoginResponse
import com.example.uts_tam_tamansafari.data.model.RegisterRequest
import com.example.uts_tam_tamansafari.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("users/add")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
