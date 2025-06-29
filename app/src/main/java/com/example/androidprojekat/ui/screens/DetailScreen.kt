package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidprojekat.ui.components.TopBar
import com.example.androidprojekat.viewmodel.MainViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val selectedPackage by viewModel.selectedPackage.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = selectedPackage?.title ?: "Detalji",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        selectedPackage?.let { pkg ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = "Naziv: ${pkg.title}", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Opis: ${pkg.notes ?: "Nema opisa"}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Organizacija: ${pkg.organization?.title ?: "Nije dostupno"}", style = MaterialTheme.typography.bodyMedium)
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ){
                Text(text = "Nema odabranog skupa podataka")
            }

        }
    }
}