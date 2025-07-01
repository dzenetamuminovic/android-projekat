package com.example.androidprojekat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidprojekat.ui.screens.BarChartScreen
import com.example.androidprojekat.ui.screens.HomeScreen
import com.example.androidprojekat.ui.screens.FavouritesScreen
import com.example.androidprojekat.ui.screens.SplashScreen
import com.example.androidprojekat.ui.screens.OnboardingScreen
import com.example.androidprojekat.ui.screens.expireddlcards.ExpiredDLCardsScreen
import com.example.androidprojekat.ui.screens.issuedidcards.IssuedIdCardsScreen
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.FavouritesViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    universalViewModel: UniversalViewModel,
    issuedIdCardsViewModel: IssuedIdCardsViewModel,
    expiredDLCardsViewModel: ExpiredDLCardsViewModel,
    favouritesViewModel: FavouritesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
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
        composable("issued_cards") {
            IssuedIdCardsScreen(
                viewModel = issuedIdCardsViewModel,
                universalViewModel = universalViewModel,
                favouritesViewModel = favouritesViewModel,
                navController = navController
            )
        }
        composable("expired_dl_cards") {
            ExpiredDLCardsScreen(
                viewModel = expiredDLCardsViewModel,
                universalViewModel = universalViewModel,
                favouritesViewModel = favouritesViewModel,
                navController = navController
            )
        }
        composable("favourites") {
            FavouritesScreen(
                favouritesViewModel = favouritesViewModel,
                navController = navController,
                universalViewModel = universalViewModel
            )
        }
        composable("statistics") {
            BarChartScreen(navController = navController, universalViewModel = universalViewModel)
        }

    }
}
