package com.example.uts_tam_tamansafari.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
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
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

data class Kebutuhan(val id: Int, val nama: String, val jumlah: String, val lokasi: String, val tanggal: String, val imageRes: Int?)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KebutuhanListScreen(
    onBack: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onNavigateTo: (String) -> Unit
) {
    val kebutuhanList = listOf(
        Kebutuhan(1, "Beras", "50 kg", "Lokasi: Jakarta", "20 Mei 2025", R.drawable.beras),
        Kebutuhan(2, "Cabai", "10 kg", "Lokasi: Bogor", "18 Mei 2025", R.drawable.cabai),
        Kebutuhan(3, "Bawang Merah", "20 kg", "Lokasi: Depok", "15 Mei 2025", R.drawable.bawang_merah),
        Kebutuhan(4, "Bawang Putih", "15 kg", "Lokasi: Jakarta", "14 Mei 2025", R.drawable.bawang_putih),
        Kebutuhan(5, "Tomat", "30 kg", "Lokasi: Tangerang", "12 Mei 2025", R.drawable.tomat)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Data Kebutuhan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah", tint = GreenPrimary)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(currentRoute = Screen.KebutuhanList.route, onNavigateTo = onNavigateTo)
        }
    ) { paddingValues ->
        // Tab filter (ScrollableTabRow) sudah dihapus agar langsung menampilkan list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(kebutuhanList) { item ->
                KebutuhanItem(item = item, onClick = { onItemClick(item.id) })
            }
        }
    }
}

@Composable
fun KebutuhanItem(item: Kebutuhan, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray
            ) {
                item.imageRes?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.nama, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = item.jumlah, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = item.lokasi, fontSize = 12.sp, color = Color.Gray)
                Text(text = item.tanggal, fontSize = 10.sp, color = Color.Gray)
            }
            
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}
