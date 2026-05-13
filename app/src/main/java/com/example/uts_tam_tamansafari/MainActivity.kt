package com.example.uts_tam_tamansafari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uts_tam_tamansafari.ui.screens.*
import com.example.uts_tam_tamansafari.ui.theme.UTS_TAM_TAManSafariTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { navController.navigate(Screen.Dashboard.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { navController.navigate(Screen.Login.route) },
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.KebutuhanList.route) {
            KebutuhanListScreen(
                onBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.TambahKebutuhan.route) },
                onItemClick = { id -> navController.navigate("${Screen.DetailKebutuhan.route}/$id") },
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.TambahKebutuhan.route) {
            TambahKebutuhanScreen(
                onBack = { navController.popBackStack() },
                onSimpan = { navController.popBackStack() }
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
                onEdit = { /* Handle edit */ },
                onDelete = { navController.popBackStack() }
            )
        }
        composable(Screen.Matching.route) {
            MatchingScreen(
                onBack = { navController.popBackStack() },
                onDetailClick = { _ -> navController.navigate(Screen.DetailMatching.route) },
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.DetailMatching.route) {
            DetailMatchingScreen(
                onBack = { navController.popBackStack() },
                onAjukanTransaksi = { navController.navigate(Screen.StatusTransaksi.route) }
            )
        }
        composable(Screen.StatusTransaksi.route) {
            StatusTransaksiScreen(
                onBack = { navController.popBackStack() },
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = { 
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
    }
}
