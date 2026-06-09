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

    // Simulasi database lokal untuk Kebutuhan (Data Dummy)
    private val _listKebutuhan = MutableStateFlow(mutableListOf(
        Kebutuhan(1, "Beras", "100kg", "Pasar Rebo", "Butuh cepat"),
        Kebutuhan(2, "Cabai Merah", "20kg", "Bogor", "Kualitas premium")
    ))
    val listKebutuhan: StateFlow<List<Kebutuhan>> = _listKebutuhan

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

    fun addKebutuhan(kebutuhan: Kebutuhan) {
        val currentList = _listKebutuhan.value.toMutableList()
        currentList.add(kebutuhan)
        _listKebutuhan.value = currentList
    }
}
