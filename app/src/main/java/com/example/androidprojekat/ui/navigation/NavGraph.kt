package com.example.androidprojekat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidprojekat.ui.screens.HomeScreen
import com.example.androidprojekat.ui.screens.DetailScreen
import com.example.androidprojekat.ui.screens.SplashScreen
import com.example.androidprojekat.ui.screens.OnboardingScreen
import com.example.androidprojekat.ui.screens.issuedidcards.IssuedIdCardsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        composable("detail") {
            DetailScreen(navController = navController)
        }
        composable("issued_cards") {
            IssuedIdCardsScreen()
        }
    }
}
