package com.example.uts_tam_tamansafari.data.repository

import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object KebutuhanRepository {

    private val _listKebutuhan =
        MutableStateFlow<List<Kebutuhan>>(emptyList())

    val listKebutuhan: StateFlow<List<Kebutuhan>> = _listKebutuhan

    fun addKebutuhan(kebutuhan: Kebutuhan) {
        _listKebutuhan.value += kebutuhan
    }

    fun deleteKebutuhan(id: Int) {
        _listKebutuhan.value =
            _listKebutuhan.value.filter { it.id != id }
    }

    fun updateKebutuhan(updated: Kebutuhan) {
        _listKebutuhan.value =
            _listKebutuhan.value.map {
                if (it.id == updated.id)
                    updated
                else
                    it
            }
    }

    fun updateStatus(id: Int, status: String) {
        _listKebutuhan.value =
            _listKebutuhan.value.map {
                if (it.id == id)
                    it.copy(status = status)
                else
                    it
            }
    }
}