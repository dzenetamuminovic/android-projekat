package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidprojekat.ui.navigation.NavGraph
import com.example.androidprojekat.ui.components.TopBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.repository.IssuedIdCardsRepository
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModelFactory
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val favouritesDao = DatabaseProvider.getDatabase(context).favouritesDao()
    val favouritesRepository = FavouritesRepository(favouritesDao)
    val universalFactory = UniversalViewModelFactory(favouritesRepository)
    val universalViewModel: UniversalViewModel = viewModel(factory = universalFactory)

    val issuedCardsRepository = IssuedIdCardsRepository()
    val issuedFactory = IssuedIdCardsViewModelFactory(issuedCardsRepository, universalViewModel)
    val issuedIdCardsViewModel: IssuedIdCardsViewModel = viewModel(factory = issuedFactory)

    Scaffold(
        topBar = {
            if (currentRoute != "splash" && currentRoute != "onboarding") {
                TopBar(
                    title = "ODP BiH App",
                    onBackClick = {
                        val popped = navController.popBackStack()
                        if (!popped) {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = false }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavGraph(
                navController = navController,
                universalViewModel = universalViewModel,
                issuedIdCardsViewModel = issuedIdCardsViewModel
            )
        }
    }
}
