package com.example.uts_tam_tamansafari.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahKebutuhanScreen(
    onBack: () -> Unit,
    onSimpan: () -> Unit,
    kebutuhanId: Int? = null,
    viewModel: KebutuhanViewModel = viewModel()
) {
    val listKebutuhan by viewModel.listKebutuhan.collectAsState()
    val kebutuhan = listKebutuhan.find { it.id == kebutuhanId }

    var selectedKomoditas by remember {
        mutableStateOf(kebutuhan?.komoditas ?: "Pilih Komoditas")
    }

    var jumlah by remember {
        mutableStateOf(kebutuhan?.jumlah ?: "")
    }

    var lokasi by remember {
        mutableStateOf(kebutuhan?.lokasi ?: "")
    }

    var catatan by remember {
        mutableStateOf(kebutuhan?.catatan ?: "")
    }

    var expanded by remember { mutableStateOf(false) }

    var komoditasError by remember { mutableStateOf("") }
    var jumlahError by remember { mutableStateOf("") }
    var lokasiError by remember { mutableStateOf("") }

    val komoditasOptions = listOf(
        "Beras",
        "Cabai",
        "Bawang Merah",
        "Bawang Putih",
        "Tomat"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (kebutuhanId == null)
                            "Tambah Kebutuhan"
                        else
                            "Edit Kebutuhan",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Pilih Komoditas", fontWeight = FontWeight.Bold)
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedKomoditas,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    komoditasOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedKomoditas = selectionOption
                                expanded = false
                                komoditasError = ""
                            }
                        )
                    }
                }
            }

            if (komoditasError.isNotEmpty()) {
                Text(
                    text = komoditasError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "Jumlah (kg)",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = jumlah,
                onValueChange = {
                    if (it.all { c -> c.isDigit() }) {
                        jumlah = it
                        jumlahError = ""
                    }
                },
                placeholder = { Text("Masukkan jumlah") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = jumlahError.isNotEmpty(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            if (jumlahError.isNotEmpty()) {
                Text(
                    text = jumlahError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "Lokasi",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = lokasi,
                onValueChange = {
                    if (it.length <= 50) {
                        lokasi = it
                        lokasiError = ""
                    }
                },
                placeholder = { Text("Masukkan lokasi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = lokasiError.isNotEmpty()
            )

            if (lokasiError.isNotEmpty()) {
                Text(
                    text = lokasiError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "Catatan (opsional)",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = catatan,
                onValueChange = {
                    if (it.length <= 100)
                        catatan = it
                },
                placeholder = {
                    Text("Tambahkan catatan...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    komoditasError = ""
                    jumlahError = ""
                    lokasiError = ""

                    when {

                        selectedKomoditas == "Pilih Komoditas" -> {
                            komoditasError = "Silakan pilih komoditas"
                        }

                        jumlah.isBlank() -> {
                            jumlahError = "Jumlah tidak boleh kosong"
                        }

                        jumlah.toIntOrNull() == null -> {
                            jumlahError = "Jumlah harus berupa angka"
                        }

                        jumlah.toInt() <= 0 -> {
                            jumlahError = "Jumlah minimal 1 kg"
                        }

                        jumlah.toInt() > 1000 -> {
                            jumlahError = "Jumlah maksimal 1000 kg"
                        }

                        lokasi.isBlank() -> {
                            lokasiError = "Lokasi tidak boleh kosong"
                        }

                        else -> {

                            if (kebutuhanId == null) {

                                viewModel.tambahKebutuhan(
                                    selectedKomoditas,
                                    jumlah,
                                    lokasi,
                                    catatan
                                )

                            } else {

                                viewModel.updateKebutuhan(
                                    kebutuhan!!.copy(
                                        komoditas = selectedKomoditas,
                                        jumlah = jumlah,
                                        lokasi = lokasi,
                                        catatan = catatan,
                                        imageRes = viewModel.getImageRes(selectedKomoditas)
                                    )
                                )
                            }

                            onSimpan()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                )
            ) {
                Text(
                    if (kebutuhanId == null) "Simpan" else "Update",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
