package com.example.uts_tam_tamansafari.ui.screens

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
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahKebutuhanScreen(
    onBack: () -> Unit,
    onSimpan: () -> Unit
) {
    var selectedKomoditas by remember { mutableStateOf("Pilih Komoditas") }
    var jumlah by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val komoditasOptions = listOf("Beras", "Cabai", "Bawang Merah", "Bawang Putih", "Tomat")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tambah Kebutuhan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                            }
                        )
                    }
                }
            }

            Text(text = "Jumlah (kg)", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = jumlah,
                onValueChange = { jumlah = it },
                placeholder = { Text("Masukkan jumlah") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(text = "Lokasi", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = lokasi,
                onValueChange = { lokasi = it },
                placeholder = { Text("Masukkan lokasi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(text = "Catatan (opsional)", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it },
                placeholder = { Text("Tambahkan catatan...") },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSimpan,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Simpan", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
