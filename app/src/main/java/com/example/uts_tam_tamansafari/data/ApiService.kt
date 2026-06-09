package com.example.uts_tam_tamansafari.data

import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
