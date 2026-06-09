package com.example.uts_tam_tamansafari.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.ui.navigation.BottomNavigationBar
import com.example.uts_tam_tamansafari.ui.navigation.Screen

data class Transaksi(val id: Int, val nama: String, val petani: String, val status: String, val statusColor: Color, val tanggal: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusTransaksiScreen(
    onBack: () -> Unit,
    onNavigateTo: (String) -> Unit
) {
    val transaksiList = listOf(
        Transaksi(1, "Beras - 50 kg", "Petani A", "Diproses", Color(0xFF2196F3), "21 Mei 2025"),
        Transaksi(2, "Cabai - 10 kg", "Petani C", "Disetujui", Color(0xFFFF9800), "20 Mei 2025"),
        Transaksi(3, "Bawang Merah - 20 kg", "Petani D", "Selesai", Color(0xFF4CAF50), "18 Mei 2025")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Status Transaksi", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Screen.StatusTransaksi.route,
                onNavigateTo = onNavigateTo
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(transaksiList) { transaksi ->
                TransaksiItem(transaksi = transaksi)
            }
        }
    }
}

@Composable
fun TransaksiItem(transaksi: Transaksi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_distriagri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = transaksi.nama, fontWeight = FontWeight.Bold)
                Text(text = transaksi.petani, fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = transaksi.status, color = transaksi.statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(text = transaksi.tanggal, fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}
