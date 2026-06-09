package com.example.uts_tam_tamansafari.data.repository

import com.example.uts_tam_tamansafari.RetrofitClient
import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import com.example.uts_tam_tamansafari.data.model.LoginRequest
import com.example.uts_tam_tamansafari.data.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Repository {
    private val api = RetrofitClient.instance

    // Simulasi database lokal untuk Kebutuhan (Data Dummy)
    private val _listKebutuhan = MutableStateFlow(mutableListOf(
        Kebutuhan(1, "Beras", "100kg", "Pasar Rebo", "Butuh cepat"),
        Kebutuhan(2, "Cabai Merah", "20kg", "Bogor", "Kualitas premium")
    ))
    val listKebutuhan: StateFlow<List<Kebutuhan>> = _listKebutuhan

    suspend fun login(username: String, password: String): LoginResponse {
        return api.login(LoginRequest(username, password))
    }

    fun addKebutuhan(kebutuhan: Kebutuhan) {
        val currentList = _listKebutuhan.value.toMutableList()
        currentList.add(kebutuhan)
        _listKebutuhan.value = currentList
    }
}
