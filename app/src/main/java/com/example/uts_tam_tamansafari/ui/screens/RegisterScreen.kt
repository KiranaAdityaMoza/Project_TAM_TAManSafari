package com.example.uts_tam_tamansafari.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary
import com.example.uts_tam_tamansafari.ui.theme.UTS_TAM_TAManSafariTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    // State for Role Dropdown
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Pelanggan", "Petani")
    var selectedRole by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Daftar Akun",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Buat akun baru untuk bergabung",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomInputField(value = name, onValueChange = { name = it }, placeholder = "Nama Lengkap", icon = Icons.Default.Person)
        
        Spacer(modifier = Modifier.height(16.dp))

        // Role field with Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                placeholder = { Text("Pilih Role", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Gray) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFCCCCCC),
                    unfocusedBorderColor = Color(0xFFEEEEEE),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                roles.forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role) },
                        onClick = {
                            selectedRole = role
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = phone, onValueChange = { phone = it }, placeholder = "Nomor HP", icon = Icons.Default.Phone)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = email, onValueChange = { email = it }, placeholder = "Email", icon = Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(value = password, onValueChange = { password = it }, placeholder = "Password", icon = Icons.Default.Lock, isPassword = true)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("Daftar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
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
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
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
            unfocusedBorderColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    UTS_TAM_TAManSafariTheme {
        RegisterScreen({}, {})
    }
}
