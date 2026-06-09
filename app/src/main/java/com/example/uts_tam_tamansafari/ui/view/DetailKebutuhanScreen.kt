package com.example.uts_tam_tamansafari.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKebutuhanScreen(
    kebutuhanId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    viewModel: KebutuhanViewModel = viewModel()
) {
    val listKebutuhan by viewModel.listKebutuhan.collectAsState()
    val kebutuhan = listKebutuhan.find { it.id == kebutuhanId }

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
        if (kebutuhan == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Data tidak ditemukan")
            }
        } else {
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
                        Image(
                            painter = painterResource(id = R.drawable.logo_distriagri),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = kebutuhan.komoditas, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = kebutuhan.jumlah, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
                        Text(text = kebutuhan.lokasi, color = Color.Gray)
                        Text(text = "Status: ${kebutuhan.status}", fontSize = 12.sp, color = Color(0xFFFFA500), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Catatan", fontWeight = FontWeight.Bold)
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Text(
                        text = kebutuhan.catatan.ifEmpty { "Tidak ada catatan." },
                        modifier = Modifier.padding(16.dp),
                        color = Color.DarkGray
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
}
