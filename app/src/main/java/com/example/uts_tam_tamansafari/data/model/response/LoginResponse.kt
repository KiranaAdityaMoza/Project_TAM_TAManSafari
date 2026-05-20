package com.example.uts_tam_tamansafari.data.model.response

data class LoginResponse(
    val id: Int,
    val username: String,
    val token: String,
    val firstName: String,
    val lastName: String
)