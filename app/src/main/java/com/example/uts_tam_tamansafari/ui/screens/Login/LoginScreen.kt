package com.example.uts_tam_tamansafari.ui.screens.Login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.data.repository.SessionManager
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

@Composable
fun LoginScreen(
    onNavigateToDashboard: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val uiState = viewModel.uiState

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                sessionManager.saveAuthToken(uiState.token)
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                onNavigateToDashboard()
            }
            is LoginUiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    LoginContent(
        username = username,
        password = password,
        isLoading = uiState is LoginUiState.Loading,
        onUsernameChange = { username = it },
        onPasswordChange = { password = it },
        onLoginClick = {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password, onSuccess = {})
            } else {
                Toast.makeText(context, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        },
        onRegisterClick = onRegisterClick
    )
}

@Composable
fun LoginContent(
    username: String,
    password: String,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pemandangan),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.5f)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_distriagri),
                contentDescription = "Logo DistriAgri",
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Platform Distribusi Hasil Pertanian",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text("Email atau Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.DarkGray,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.DarkGray,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", fontSize = 16.sp, color = Color.White)
                }
            }

            TextButton(onClick = {}) {
                Text("Lupa Password?", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Belum punya akun? ", color = Color.Black)
                TextButton(onClick = onRegisterClick) {
                    Text("Daftar di sini", color = GreenPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}