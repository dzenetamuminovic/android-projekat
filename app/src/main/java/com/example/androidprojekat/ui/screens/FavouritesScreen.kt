package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidprojekat.data.FavouritesRepository
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.data.local.FavouritesItem
import com.example.androidprojekat.viewmodel.FavouritesViewModel

@Composable
fun FavouritesScreen() {
    val context = LocalContext.current
    val dao = DatabaseProvider.getDatabase(context).favouritesDao()
    val repository = FavouritesRepository(dao)
    val viewModel = remember { FavouritesViewModel(repository) }

    val favourites by viewModel.favourites.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Sačuvani podaci", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (favourites.isEmpty()) {
            Text("Nemate sačuvanih podataka.")
        } else {
            LazyColumn {
                items(favourites) { item ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Institucija: ${item.institution}", style = MaterialTheme.typography.titleMedium)
                            Text("Entitet: ${item.entity}")
                            Text("Kanton: ${item.canton}")
                            Text("Općina: ${item.municipality}")
                            Text("Ukupno izdato: ${item.total}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = {
                                viewModel.removeFavourites(item)
                            }) {
                                Text("Ukloni iz sačuvanih")
                            }
                        }
                    }
                }
            }
        }
    }
}
