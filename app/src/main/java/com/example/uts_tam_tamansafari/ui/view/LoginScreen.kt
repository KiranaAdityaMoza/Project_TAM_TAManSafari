package com.example.uts_tam_tamansafari.ui.view

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.R
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.utils.SessionManager
import com.example.uts_tam_tamansafari.ui.viewmodel.LoginViewModel

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(
        val token: String,
        val username: String,
        val firstName: String,
        val lastName: String
    ) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

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
                sessionManager.saveUserDetail(
                    username = uiState.username,
                    fullName = "${uiState.firstName} ${uiState.lastName}"
                )
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                onNavigateToDashboard()
                viewModel.resetState()
            }
            is LoginUiState.Error -> {
                val customMessage = if (uiState.message.contains("400") || uiState.message.contains("Bad Request", ignoreCase = true)) {
                    "Maaf, akun Anda belum terdaftar atau kombinasi salah."
                } else {
                    uiState.message
                }
                Toast.makeText(context, customMessage, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pemandangan),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_distriagri),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp).clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        val trimmedUser = username.trim()
                        val trimmedPass = password.trim()

                        if (trimmedUser == sessionManager.getLocalUsername() && trimmedPass == sessionManager.getLocalPassword()) {
                            sessionManager.saveAuthToken("mock_local_token_distriagri")
                            sessionManager.saveUserDetail(trimmedUser, trimmedUser)
                            Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                            onNavigateToDashboard()
                        } else {
                            viewModel.login(trimmedUser, trimmedPass)
                        }
                    } else {
                        Toast.makeText(context, "Isi semua bidang!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = uiState !is LoginUiState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Login", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onRegisterClick) {
                Text("Belum punya akun? Daftar di sini", color = GreenPrimary)
            }
        }
    }
}