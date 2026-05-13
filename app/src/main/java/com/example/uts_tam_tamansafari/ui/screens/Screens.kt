package com.example.uts_tam_tamansafari.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.uts_tam_tamansafari.ui.theme.GreenPrimary

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object KebutuhanList : Screen("kebutuhan_list")
    object TambahKebutuhan : Screen("tambah_kebutuhan")
    object DetailKebutuhan : Screen("detail_kebutuhan")
    object Matching : Screen("matching")
    object DetailMatching : Screen("detail_matching")
    object StatusTransaksi : Screen("status_transaksi")
    object Profile : Screen("profile")
}

@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigateTo: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        val navItems = listOf(
            Triple(Screen.Dashboard.route, Icons.Default.Home, "Beranda"),
            Triple(Screen.KebutuhanList.route, Icons.Default.List, "Kebutuhan"),
            Triple(Screen.Matching.route, Icons.Default.Search, "Matching"),
            Triple(Screen.StatusTransaksi.route, Icons.Default.ShoppingCart, "Status"),
            Triple(Screen.Profile.route, Icons.Default.Person, "Akun")
        )

        navItems.forEach { (route, icon, label) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { onNavigateTo(route) },
                icon = { Icon(icon, contentDescription = null) },
                label = { 
                    Text(
                        text = label,
                        fontSize = 10.sp, // Ukuran font sedikit diperkecil agar muat
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenPrimary,
                    selectedTextColor = GreenPrimary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}