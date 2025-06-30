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
import com.example.androidprojekat.data.RetrofitInstance
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.repository.IssuedIdCardsRepository
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.Factory.IssuedIdCardsViewModelFactory
import com.example.androidprojekat.viewmodel.Factory.ExpiredDLCardsViewModelFactory
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.Factory.UniversalViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val favouritesDao = DatabaseProvider.getDatabase(context).favouritesDao()
    val favouritesRepository = FavouritesRepository(favouritesDao)
    val expiredDLCardsRepository = ExpiredDLCardsRepository(RetrofitInstance.expiredDLCardsApi, context)

    val universalFactory = UniversalViewModelFactory(favouritesRepository, expiredDLCardsRepository)
    val universalViewModel: UniversalViewModel = viewModel(factory = universalFactory)

    val issuedCardsRepository = IssuedIdCardsRepository(context)
    val issuedFactory = IssuedIdCardsViewModelFactory(issuedCardsRepository, universalViewModel)
    val issuedIdCardsViewModel: IssuedIdCardsViewModel = viewModel(factory = issuedFactory)

    val expiredFactory = ExpiredDLCardsViewModelFactory(expiredDLCardsRepository, universalViewModel)
    val expiredDLCardsViewModel: ExpiredDLCardsViewModel = viewModel(factory = expiredFactory)

    Scaffold(
        topBar = {
            if (currentRoute != "splash" && currentRoute != "onboarding") {
                TopBar(
                    title = "IDDEEA OpenData",
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
                issuedIdCardsViewModel = issuedIdCardsViewModel,
                expiredDLCardsViewModel = expiredDLCardsViewModel
            )
        }
    }
}
