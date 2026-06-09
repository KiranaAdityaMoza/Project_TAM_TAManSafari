package com.example.uts_tam_tamansafari.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.viewmodel.RegisterUiState
import com.example.uts_tam_tamansafari.ui.viewmodel.RegisterViewModel
import com.example.uts_tam_tamansafari.utils.SessionManager

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val uiState = viewModel.uiState

    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Success -> {
                sessionManager.saveLocalRegister(username.trim(), password.trim(), email.trim())
                Toast.makeText(context, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
                viewModel.resetState()
            }
            is RegisterUiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(text = "Daftar Akun", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = "Bergabung sebagai Pembeli di DistriAgri", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.height(32.dp))

        CustomInputField(value = firstName, onValueChange = { firstName = it }, placeholder = "Nama Depan", icon = Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = lastName, onValueChange = { lastName = it }, placeholder = "Nama Belakang", icon = Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = username, onValueChange = { username = it }, placeholder = "Username", icon = Icons.Default.AccountCircle)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = email, onValueChange = { email = it }, placeholder = "Email", icon = Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = password, onValueChange = { password = it }, placeholder = "Password", icon = Icons.Default.Lock, isPassword = true)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                    sessionManager.saveLocalRegister(username.trim(), password.trim(), email.trim())
                    viewModel.register(username.trim(), email.trim(), password.trim(), firstName.trim(), lastName.trim())
                } else {
                    Toast.makeText(context, "Isi semua bidang!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            enabled = uiState !is RegisterUiState.Loading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            if (uiState is RegisterUiState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Daftar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Sudah punya akun? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Login di sini",
                color = GreenPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Composable
fun CustomInputField(value: String, onValueChange: (String) -> Unit, placeholder: String, icon: ImageVector, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFCCCCCC),
            unfocusedBorderColor = Color(0xFFEEEEEE)
        )
    )
}