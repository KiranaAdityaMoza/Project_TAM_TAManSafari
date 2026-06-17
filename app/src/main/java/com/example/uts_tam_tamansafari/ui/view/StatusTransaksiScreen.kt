package com.example.uts_tam_tamansafari.ui.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.data.repository.Transaksi
import com.example.uts_tam_tamansafari.ui.navigation.BottomNavigationBar
import com.example.uts_tam_tamansafari.ui.navigation.Screen
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import androidx.compose.ui.res.painterResource
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusTransaksiScreen(
    onBack: () -> Unit,
    onNavigateTo: (String) -> Unit,
    viewModel: KebutuhanViewModel = viewModel()
) {
    val transaksiList by viewModel.listTransaksi.collectAsState()

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
        if (transaksiList.isEmpty()) {
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
                    Text("Belum ada transaksi.", color = Color.Gray)
                    Text("Ajukan transaksi dari hasil matching.", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${transaksi.produk.namaProduk} - ${transaksi.produk.namaPetani}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Status: ${transaksi.status}", fontSize = 12.sp, color = GreenPrimary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = transaksi.status, color = GreenPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(text = transaksi.tanggal, fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}