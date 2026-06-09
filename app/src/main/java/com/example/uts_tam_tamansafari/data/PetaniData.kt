package com.example.uts_tam_tamansafari.data

data class ProdukPetani(
    val id: Int,
    val namaPetani: String,
    val namaProduk: String,
    val harga: Int,
    val lokasi: String,
    val gambarRes: Int // Menggunakan resource ID seperti R.drawable.beras
)

object PetaniData {
    val listProdukPetani = listOf(
        ProdukPetani(1, "Pak Budi", "Beras Putih", 15000, "Cianjur", com.example.uts_tam_tamansafari.R.drawable.beras),
        ProdukPetani(2, "Ibu Siti", "Cabai Merah", 45000, "Sukabumi", com.example.uts_tam_tamansafari.R.drawable.cabai),
        ProdukPetani(3, "Pak Agus", "Tomat Segar", 12000, "Garut", com.example.uts_tam_tamansafari.R.drawable.tomat),
        ProdukPetani(4, "Pak Bambang", "Bawang Merah", 30000, "Brebes", com.example.uts_tam_tamansafari.R.drawable.bawang_merah),
        ProdukPetani(5, "Ibu Ani", "Bawang Putih", 35000, "Brebes", com.example.uts_tam_tamansafari.R.drawable.bawang_putih)
    )
}
