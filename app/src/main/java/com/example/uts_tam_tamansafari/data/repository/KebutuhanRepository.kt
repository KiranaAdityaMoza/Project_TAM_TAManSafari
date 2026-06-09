package com.example.uts_tam_tamansafari.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.data.PetaniData
import com.example.uts_tam_tamansafari.data.ProdukPetani
import com.example.uts_tam_tamansafari.data.model.Kebutuhan
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Transaksi(
    val id: Int,
    val produk: ProdukPetani,
    val status: String = "Menunggu Konfirmasi",
    val tanggal: String
)

object KebutuhanRepository {
    private const val PREF_NAME = "distriagri_prefs" // Menggunakan file yang sama dengan SessionManager
    private const val KEY_KEBUTUHAN = "perm_list_kebutuhan"
    private const val KEY_MATCHING = "perm_matching_results"
    private const val KEY_TRANSAKSI = "perm_list_transaksi"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    private val _listKebutuhan = MutableStateFlow<List<Kebutuhan>>(emptyList())
    val listKebutuhan: StateFlow<List<Kebutuhan>> = _listKebutuhan

    private val _matchingResults = MutableStateFlow<List<ProdukPetani>>(emptyList())
    val matchingResults: StateFlow<List<ProdukPetani>> = _matchingResults

    private val _listTransaksi = MutableStateFlow<List<Transaksi>>(emptyList())
    val listTransaksi: StateFlow<List<Transaksi>> = _listTransaksi

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadData()
    }

    // Fungsi untuk mendapatkan ID gambar terbaru berdasarkan nama komoditas
    private fun getFreshImageRes(komoditas: String): Int {
        return when {
            komoditas.contains("Beras", true) -> R.drawable.beras
            komoditas.contains("Cabai", true) -> R.drawable.cabai
            komoditas.contains("Bawang Merah", true) -> R.drawable.bawang_merah
            komoditas.contains("Bawang Putih", true) -> R.drawable.bawang_putih
            komoditas.contains("Tomat", true) -> R.drawable.tomat
            else -> R.drawable.beras
        }
    }

    private fun saveData() {
        // Menggunakan commit() agar data langsung tertulis secara sinkron ke storage
        prefs.edit().apply {
            putString(KEY_KEBUTUHAN, gson.toJson(_listKebutuhan.value))
            putString(KEY_MATCHING, gson.toJson(_matchingResults.value))
            putString(KEY_TRANSAKSI, gson.toJson(_listTransaksi.value))
            commit() 
        }
    }

    private fun loadData() {
        val jsonKebutuhan = prefs.getString(KEY_KEBUTUHAN, null)
        val jsonMatching = prefs.getString(KEY_MATCHING, null)
        val jsonTransaksi = prefs.getString(KEY_TRANSAKSI, null)

        if (jsonKebutuhan != null) {
            val type = object : TypeToken<List<Kebutuhan>>() {}.type
            val loaded: List<Kebutuhan>? = gson.fromJson(jsonKebutuhan, type)
            _listKebutuhan.value = loaded?.map { it.copy(imageRes = getFreshImageRes(it.komoditas)) } ?: emptyList()
        }
        
        if (jsonMatching != null) {
            val type = object : TypeToken<List<ProdukPetani>>() {}.type
            val loaded: List<ProdukPetani>? = gson.fromJson(jsonMatching, type)
            _matchingResults.value = loaded?.map { p ->
                val fresh = PetaniData.listProdukPetani.find { it.id == p.id }
                p.copy(gambarRes = fresh?.gambarRes ?: p.gambarRes)
            } ?: emptyList()
        }

        if (jsonTransaksi != null) {
            val type = object : TypeToken<List<Transaksi>>() {}.type
            val loaded: List<Transaksi>? = gson.fromJson(jsonTransaksi, type)
            _listTransaksi.value = loaded?.map { t ->
                val fresh = PetaniData.listProdukPetani.find { it.id == t.produk.id }
                t.copy(produk = t.produk.copy(gambarRes = fresh?.gambarRes ?: t.produk.gambarRes))
            } ?: emptyList()
        }
    }

    fun addKebutuhan(kebutuhan: Kebutuhan) {
        _listKebutuhan.value = _listKebutuhan.value + kebutuhan
        saveData()
    }

    fun deleteKebutuhan(id: Int) {
        _listKebutuhan.value = _listKebutuhan.value.filter { it.id != id }
        saveData()
    }

    fun updateKebutuhan(updated: Kebutuhan) {
        _listKebutuhan.value = _listKebutuhan.value.map {
            if (it.id == updated.id) updated else it
        }
        saveData()
    }

    fun updateStatus(id: Int, status: String) {
        _listKebutuhan.value = _listKebutuhan.value.map {
            if (it.id == id) it.copy(status = status) else it
        }
        saveData()
    }

    fun performMatching(kebutuhanId: Int) {
        val kebutuhan = _listKebutuhan.value.find { it.id == kebutuhanId }
        if (kebutuhan != null) {
            val matches = PetaniData.listProdukPetani.filter {
                it.namaProduk.contains(kebutuhan.komoditas, ignoreCase = true)
            }
            _matchingResults.value = (_matchingResults.value + matches).distinctBy { it.id }
            updateStatus(kebutuhanId, "Sudah Matching")
            saveData()
        }
    }

    fun ajukanTransaksi(produk: ProdukPetani) {
        val newId = (_listTransaksi.value.maxOfOrNull { it.id } ?: 0) + 1
        val tanggal = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val transaksi = Transaksi(id = newId, produk = produk, tanggal = tanggal)
        _listTransaksi.value = _listTransaksi.value + transaksi
        saveData()
    }
}
