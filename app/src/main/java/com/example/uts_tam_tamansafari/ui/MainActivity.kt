package com.example.uts_tam_tamansafari.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uts_tam_tamansafari.ui.navigation.Screen
import com.example.uts_tam_tamansafari.ui.view.*
import com.example.uts_tam_tamansafari.ui.theme.UTS_TAM_TAManSafariTheme
import com.example.uts_tam_tamansafari.utils.SessionManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uts_tam_tamansafari.data.repository.KebutuhanRepository
import com.example.uts_tam_tamansafari.ui.viewmodel.KebutuhanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sessionManager = SessionManager(this)
        // Inisialisasi Repository dengan username yang sedang login (jika ada)
        KebutuhanRepository.init(this, sessionManager.getUserName())
        
        enableEdgeToEdge()
        setContent {
            UTS_TAM_TAManSafariTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ViewModel didefinisikan di sini agar bisa di-share antar layar
    val kebutuhanViewModel: KebutuhanViewModel = viewModel()
    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    val startDestination = if (sessionManager.isLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToDashboard = {
                    // Update user di repository saat login berhasil
                    KebutuhanRepository.setUser(sessionManager.getUserName() ?: "guest")
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { 
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                kebutuhanViewModel = kebutuhanViewModel
            )
        }
        composable(Screen.KebutuhanList.route) {
            KebutuhanListScreen(
                onBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.TambahKebutuhan.route) },
                onItemClick = { id -> navController.navigate("${Screen.DetailKebutuhan.route}/$id") },
                onNavigateTo = { route -> navController.navigate(route) },
                viewModel = kebutuhanViewModel
            )
        }
        composable(Screen.TambahKebutuhan.route) {
            TambahKebutuhanScreen(
                onBack = { navController.popBackStack() },
                onSimpan = { navController.popBackStack() },
                viewModel = kebutuhanViewModel
            )
        }

        composable( route = "edit_kebutuhan/{kebutuhanId}",
            arguments = listOf(
                navArgument("kebutuhanId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val kebutuhanId = backStackEntry.arguments?.getInt("kebutuhanId")
            TambahKebutuhanScreen(
                kebutuhanId = kebutuhanId,
                onBack = { navController.popBackStack() },
                onSimpan = { navController.popBackStack() },
                viewModel = kebutuhanViewModel
            )
        }

        composable(
            route = "${Screen.DetailKebutuhan.route}/{kebutuhanId}",
            arguments = listOf(navArgument("kebutuhanId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("kebutuhanId") ?: 1
            DetailKebutuhanScreen(
                kebutuhanId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("edit_kebutuhan/$id") },
                onDelete = { navController.popBackStack() },
                onMatching = { navController.navigate(Screen.Matching.route) },
                viewModel = kebutuhanViewModel
            )
        }
        composable(Screen.Matching.route) {
            MatchingScreen(
                onBack = { navController.popBackStack() },
                onDetailClick = { id -> navController.navigate("${Screen.DetailMatching.route}/$id") },
                onNavigateTo = { route -> navController.navigate(route) },
                viewModel = kebutuhanViewModel
            )
        }
        
        composable(
            route = "${Screen.DetailMatching.route}/{produkId}",
            arguments = listOf(navArgument("produkId") { type = NavType.IntType })
        ) { backStackEntry ->
            val produkId = backStackEntry.arguments?.getInt("produkId") ?: 0
            DetailMatchingScreen(
                produkId = produkId,
                onBack = { navController.popBackStack() },
                onAjukanTransaksi = { 
                    navController.navigate(Screen.StatusTransaksi.route) {
                        popUpTo(Screen.Matching.route) { inclusive = false }
                    }
                },
                viewModel = kebutuhanViewModel
            )
        }

        composable(Screen.StatusTransaksi.route) {
            StatusTransaksiScreen(
                onBack = { navController.popBackStack() },
                onNavigateTo = { route -> navController.navigate(route) },
                viewModel = kebutuhanViewModel
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    sessionManager.logout()
                    // Reset user di repository saat logout
                    KebutuhanRepository.setUser("guest")
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
    }
}
