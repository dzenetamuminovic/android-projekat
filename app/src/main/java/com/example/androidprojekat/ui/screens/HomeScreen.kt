package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(56.dp))

        Button(
            onClick = { navController.navigate("issued_cards") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Prikaži izdate lične karte")
        }
        Button(
            onClick = { navController.navigate("expired_dl_cards") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Prikaži nevazece vozacke dozvole")
        }
        Button(
            onClick = { navController.navigate("favourites") }) {
            Text("Prikaži favourites")
        }

    }
}
