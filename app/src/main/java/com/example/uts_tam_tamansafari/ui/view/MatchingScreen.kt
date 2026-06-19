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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.data.ProdukPetani
import com.example.uts_tam_tamansafari.ui.navigation.BottomNavigationBar
import com.example.uts_tam_tamansafari.ui.navigation.Screen
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.theme.LightGreen
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingScreen(
    onBack: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onNavigateTo: (String) -> Unit,
    viewModel: KebutuhanViewModel = viewModel()
) {
    val matchingResults by viewModel.matchingResults.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hasil Matching", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(currentRoute = Screen.Matching.route, onNavigateTo = onNavigateTo)
        }
    ) { paddingValues ->
        if (matchingResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_distriagri),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.Gray.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Belum ada hasil matching.",
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "Klik tombol 'Matching' di detail kebutuhan.",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(matchingResults) { produk ->
                    MatchingItem(produk = produk, onDetailClick = { onDetailClick(produk.id) })
                }
            }
        }
    }
}

@Composable
fun MatchingItem(produk: ProdukPetani, onDetailClick: () -> Unit) {
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
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray
            ) {
                Image(
                    painter = painterResource(id = produk.gambarRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = produk.namaProduk, fontWeight = FontWeight.Bold)
                Text(text = produk.namaPetani, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = GreenPrimary)
                // MENAMPILKAN STOK ASLI
                Text(text = "Stok: ${produk.stok} kg", fontSize = 12.sp, color = if(produk.stok < 20) Color.Red else Color.Gray)
                Text(text = "Lokasi: ${produk.lokasi}", fontSize = 12.sp, color = Color.Gray)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Surface(color = LightGreen, shape = RoundedCornerShape(4.dp)) {
                    Text(
                        text = "Rp ${produk.harga}/kg",
                        color = GreenPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onDetailClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("Detail", fontSize = 10.sp, color = Color.White)
                }
            }
        }
    }
}
