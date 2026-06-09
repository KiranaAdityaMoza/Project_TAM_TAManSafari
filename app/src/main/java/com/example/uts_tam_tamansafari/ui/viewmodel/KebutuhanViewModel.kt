package com.example.uts_tam_tamansafari.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import com.example.uts_tam_tamansafari.data.repository.Repository
import kotlinx.coroutines.flow.StateFlow

class KebutuhanViewModel(private val repository: Repository = Repository()) : ViewModel() {
    val listKebutuhan: StateFlow<List<Kebutuhan>> = repository.listKebutuhan

    fun tambahKebutuhan(komoditas: String, jumlah: String, lokasi: String, catatan: String) {
        val newId = (listKebutuhan.value.maxOfOrNull { it.id } ?: 0) + 1
        repository.addKebutuhan(Kebutuhan(newId, komoditas, jumlah, lokasi, catatan))
    }
}
