package com.example.uts_tam_tamansafari.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.ui.navigation.BottomNavigationBar
import com.example.uts_tam_tamansafari.ui.navigation.Screen
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.theme.LightGreen
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel

@Composable
fun DashboardScreen(
    onNavigateTo: (String) -> Unit,
    kebutuhanViewModel: KebutuhanViewModel = viewModel()
) {
    val listKebutuhan by kebutuhanViewModel.listKebutuhan.collectAsState()
    val matchingResults by kebutuhanViewModel.matchingResults.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute = Screen.Dashboard.route, onNavigateTo = onNavigateTo)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Halo, Pembeli!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Selamat datang di DistriAgri", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Total Kebutuhan",
                    value = listKebutuhan.size.toString(),
                    subtitle = "Data",
                    modifier = Modifier.weight(1f),
                    color = LightGreen
                )
                StatCard(
                    title = "Total Match",
                    value = matchingResults.size.toString(),
                    subtitle = "Kecocokan",
                    modifier = Modifier.weight(1f),
                    color = LightGreen
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Menu Utama", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            MenuCard(
                title = "Data Kebutuhan",
                subtitle = "Lihat dan kelola kebutuhan Anda",
                icon = Icons.AutoMirrored.Filled.List,
                onClick = { onNavigateTo(Screen.KebutuhanList.route) }
            )
            MenuCard(
                title = "Matching",
                subtitle = "Lihat hasil pencocokan tersedia",
                icon = Icons.Default.Search,
                onClick = { onNavigateTo(Screen.Matching.route) }
            )
        }
    }
}

@Composable
fun StatCard(title: String, value: String, subtitle: String, modifier: Modifier, color: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun MenuCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}
