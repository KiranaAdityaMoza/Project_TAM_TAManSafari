package com.example.uts_tam_tamansafari.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.pemandangan),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay putih dengan transparansi halus
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.5f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed to Top to allow closer spacing with Logo
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Added some top space before the logo

            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.logo_distriagri),
                contentDescription = "Logo DistriAgri",
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(4.dp)) // Reduced space even more

            Text(
                text = "Platform Distribusi Hasil Pertanian",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp)) // Reduced space to 24dp (previously 48dp)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email atau Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.DarkGray,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp)) // Reduced from 16dp

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.DarkGray,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp)) // Reduced from 24dp

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Login", fontSize = 16.sp, color = Color.White)
            }

            TextButton(onClick = { /* Handle forgot password */ }) {
                Text("Lupa Password?", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Reduced from 24dp

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Belum punya akun? ", color = Color.Black)
                TextButton(onClick = onRegisterClick) {
                    Text("Daftar di sini", color = GreenPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
