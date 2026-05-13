package com.example.uts_tam_tamansafari.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKebutuhanScreen(
    kebutuhanId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val kebutuhan = when(kebutuhanId) {
        1 -> Kebutuhan(1, "Beras", "50 kg", "Lokasi: Jakarta", "20 Mei 2025", R.drawable.beras)
        2 -> Kebutuhan(2, "Cabai", "10 kg", "Lokasi: Bogor", "18 Mei 2025", R.drawable.cabai)
        3 -> Kebutuhan(3, "Bawang Merah", "20 kg", "Lokasi: Depok", "15 Mei 2025", R.drawable.bawang_merah)
        4 -> Kebutuhan(4, "Bawang Putih", "15 kg", "Lokasi: Jakarta", "14 Mei 2025", R.drawable.bawang_putih)
        5 -> Kebutuhan(5, "Tomat", "30 kg", "Lokasi: Tangerang", "12 Mei 2025", R.drawable.tomat)
        else -> Kebutuhan(1, "Beras", "50 kg", "Lokasi: Jakarta", "20 Mei 2025", R.drawable.beras)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Kebutuhan", fontWeight = FontWeight.Bold) },
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
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.LightGray
                ) {
                    kebutuhan.imageRes?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = kebutuhan.nama, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = kebutuhan.jumlah, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = kebutuhan.lokasi, color = Color.Gray)
                    Text(text = kebutuhan.tanggal, fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Catatan", fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Text(
                    text = "Dibutuhkan untuk stok bulanan.",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Text("Hapus")
                }
                Button(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text("Edit", color = Color.White)
                }
            }
        }
    }
}
