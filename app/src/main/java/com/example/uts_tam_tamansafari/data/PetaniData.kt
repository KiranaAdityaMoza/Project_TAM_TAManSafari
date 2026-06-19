package com.example.uts_tam_tamansafari.data
import com.example.uts_tam_tamansafari.R
data class ProdukPetani(
    val id: Int,
    val namaPetani: String,
    val namaProduk: String,
    val harga: Int,
    val stok: Int,
    val lokasi: String,
    val gambarRes: Int
)

object PetaniData {
    val listProdukPetani = listOf(
        ProdukPetani(1, "Pak Budi", "Beras Putih", 15000, 500, "Cianjur", com.example.uts_tam_tamansafari.R.drawable.beras),
        ProdukPetani(6, "Pak Haji", "Beras Organik", 22000, 50, "Sukabumi", com.example.uts_tam_tamansafari.R.drawable.beras),
        ProdukPetani(7, "Kelompok Tani 01", "Beras Pandan Wangi", 18000, 1000, "Cianjur", com.example.uts_tam_tamansafari.R.drawable.beras),

        ProdukPetani(2, "Ibu Siti", "Cabai Merah", 45000, 20, "Sukabumi", com.example.uts_tam_tamansafari.R.drawable.cabai),
        ProdukPetani(8, "Pak Mamat", "Cabai Rawit", 60000, 15, "Garut", com.example.uts_tam_tamansafari.R.drawable.cabai),
        ProdukPetani(9, "Teh Rina", "Cabai Keriting", 40000, 100, "Bandung", com.example.uts_tam_tamansafari.R.drawable.cabai),

        ProdukPetani(3, "Pak Agus", "Tomat Segar", 12000, 200, "Garut", com.example.uts_tam_tamansafari.R.drawable.tomat),
        ProdukPetani(10, "Ibu Yanti", "Tomat Cherry", 25000, 10, "Lembang", com.example.uts_tam_tamansafari.R.drawable.tomat),

        ProdukPetani(4, "Pak Bambang", "Bawang Merah", 30000, 300, "Brebes", com.example.uts_tam_tamansafari.R.drawable.bawang_merah),
        ProdukPetani(11, "Pak Dedi", "Bawang Merah Super", 35000, 40, "Nganjuk", com.example.uts_tam_tamansafari.R.drawable.bawang_merah),

        ProdukPetani(5, "Ibu Ani", "Bawang Putih", 35000, 150, "Brebes", com.example.uts_tam_tamansafari.R.drawable.bawang_putih),
        ProdukPetani(12, "Pak Kurnia", "Bawang Putih Impor", 40000, 500, "Jakarta", com.example.uts_tam_tamansafari.R.drawable.bawang_putih)
    )
}
