package com.example.uts_tam_tamansafari.data.model

data class Kebutuhan(
    val id: Int,
    val komoditas: String,
    val jumlah: String,
    val lokasi: String,
    val catatan: String,
    val status: String = "Mencari Petani"
)
