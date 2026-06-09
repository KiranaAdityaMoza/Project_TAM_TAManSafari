package com.example.uts_tam_tamansafari.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import com.example.uts_tam_tamansafari.data.repository.KebutuhanRepository
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class KebutuhanViewModel : ViewModel() {

    val listKebutuhan: StateFlow<List<Kebutuhan>> =
        KebutuhanRepository.listKebutuhan

    fun getImageRes(komoditas: String): Int {
        return when (komoditas) {
            "Beras" -> R.drawable.beras
            "Cabai" -> R.drawable.cabai
            "Bawang Merah" -> R.drawable.bawang_merah
            "Bawang Putih" -> R.drawable.bawang_putih
            "Tomat" -> R.drawable.tomat
            else -> R.drawable.beras
        }
    }

    fun tambahKebutuhan(
        komoditas: String,
        jumlah: String,
        lokasi: String,
        catatan: String
    ) {
        val newId = (listKebutuhan.value.maxOfOrNull { it.id } ?: 0) + 1

        val tanggal = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        ).format(Date())

        val kebutuhan = Kebutuhan(
            id = newId,
            komoditas = komoditas,
            jumlah = jumlah,
            lokasi = lokasi,
            catatan = catatan,
            tanggal = tanggal,
            imageRes = getImageRes(komoditas)
        )

        KebutuhanRepository.addKebutuhan(kebutuhan)
    }

    fun hapusKebutuhan(id: Int) {
        KebutuhanRepository.deleteKebutuhan(id)
    }

    fun updateKebutuhan(kebutuhan: Kebutuhan) {
        KebutuhanRepository.updateKebutuhan(kebutuhan)
    }

    fun matchingKebutuhan(id: Int) {
        KebutuhanRepository.updateStatus(
            id,
            "Sudah Matching"
        )
    }
}