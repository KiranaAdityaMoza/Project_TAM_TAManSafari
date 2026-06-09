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
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.theme.LightGreen

data class MatchingResult(val id: Int, val nama: String, val petani: String, val tersedia: String, val lokasi: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingScreen(
    onBack: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onNavigateTo: (String) -> Unit
) {
    val matchingResults = listOf(
        MatchingResult(1, "Beras", "Petani A", "Tersedia: 100 kg", "Lokasi: Bekasi"),
        MatchingResult(2, "Bawang Putih", "Petani B", "Tersedia: 30 kg", "Lokasi: Karawang"),
        MatchingResult(3, "Cabai", "Petani C", "Tersedia: 50 kg", "Lokasi: Bogor")
    )

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(matchingResults) { result ->
                MatchingItem(result = result, onDetailClick = { onDetailClick(result.id) })
            }
        }
    }
}

@Composable
fun MatchingItem(result: MatchingResult, onDetailClick: () -> Unit) {
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
                    painter = painterResource(id = R.drawable.logo_distriagri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = result.nama, fontWeight = FontWeight.Bold)
                Text(text = result.petani, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = result.tersedia, fontSize = 12.sp, color = Color.Gray)
                Text(text = result.lokasi, fontSize = 12.sp, color = Color.Gray)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Surface(color = LightGreen, shape = RoundedCornerShape(4.dp)) {
                    Text(text = "Cocok", color = GreenPrimary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDetailClick, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary), modifier = Modifier.height(32.dp)) {
                    Text("Detail", fontSize = 10.sp, color = Color.White)
                }
            }
        }
    }
}
