package com.example.uts_tam_tamansafari.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
fun DetailMatchingScreen(
    onBack: () -> Unit,
    onAjukanTransaksi: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Matching", fontWeight = FontWeight.Bold) },
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
                Image(
                    painter = painterResource(id = R.drawable.petani_cowo),
                    contentDescription = "Foto Petani",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Petani A", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Lokasi: Bekasi", color = Color.Gray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                        Text(text = " 4.8 (25 ulasan)", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Informasi Komoditas", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.beras),
                        contentDescription = "Foto Beras",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = "Beras", fontWeight = FontWeight.Bold)
                        Row {
                            Text(text = "Tersedia", modifier = Modifier.width(80.dp), color = Color.Gray)
                            Text(text = ": 100 kg")
                        }
                        Row {
                            Text(text = "Harga", modifier = Modifier.width(80.dp), color = Color.Gray)
                            Text(text = ": Rp 12.000 / kg")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Informasi Lain", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DetailInfoRow(label = "Jarak", value = "15 km dari lokasi Anda")
                DetailInfoRow(label = "Catatan", value = "Beras kualitas premium")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onAjukanTransaksi,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Ajukan Transaksi", color = Color.White)
            }
        }
    }
}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Row {
        Text(text = label, modifier = Modifier.width(80.dp), color = Color.Gray)
        Text(text = ": $value")
    }
}
