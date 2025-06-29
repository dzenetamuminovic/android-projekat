package com.example.androidprojekat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidprojekat.ui.screens.HomeScreen
import com.example.androidprojekat.ui.screens.DetailScreen
import com.example.androidprojekat.ui.screens.FavouritesScreen
import com.example.androidprojekat.ui.screens.SplashScreen
import com.example.androidprojekat.ui.screens.OnboardingScreen
import com.example.androidprojekat.ui.screens.issuedidcards.IssuedIdCardsScreen
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    universalViewModel: UniversalViewModel,
    issuedIdCardsViewModel: IssuedIdCardsViewModel
)
{
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
            IssuedIdCardsScreen(
                navController = navController,
                viewModel = issuedIdCardsViewModel,
                universalViewModel = universalViewModel
            )
        }

        composable("favourites") {
            FavouritesScreen(universalViewModel = universalViewModel)
        }
    }
}
