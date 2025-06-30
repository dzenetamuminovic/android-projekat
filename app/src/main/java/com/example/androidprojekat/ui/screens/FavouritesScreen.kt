package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.ui.components.CardItem
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.utils.Share

@Composable
fun FavouritesScreen(universalViewModel: UniversalViewModel, navController: NavController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val dao = DatabaseProvider.getDatabase(context).favouritesDao()
    val repository = FavouritesRepository(dao)
    val viewModel = remember { FavouritesViewModel(repository) }

    val favourites by viewModel.favourites.collectAsState()
    var filterText by remember { mutableStateOf("") }

    val filteredFavourites = favourites.filter {
        it.institution.contains(filterText, ignoreCase = true) ||
                it.municipality.contains(filterText, ignoreCase = true) ||
                it.entity.contains(filterText, ignoreCase = true) ||
                (it.canton ?: "").contains(filterText, ignoreCase = true) ||
                it.total.toString().contains(filterText)
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, favouritesRoute = "favourites", homeRoute = "home")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {
            Text(
                text = "Sačuvani podaci",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Pretražite sačuvane podatke") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredFavourites.isEmpty()) {
                Text("Nemate sačuvanih podataka ili nema rezultata pretrage.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredFavourites) { item ->
                        CardItem(
                            title = "Institucija: ${item.institution}",
                            subtitle = "Općina: ${item.municipality}",
                            expandedContent = """
                                Entitet: ${item.entity}
                                Kanton: ${item.canton}
                                Ukupno izdato: ${item.total}
                            """.trimIndent(),
                            showDelete = true,
                            onDeleteClick = { viewModel.removeFavourites(item) },
                            onShareClick = {
                                val shareText = """
                                    Institucija: ${item.institution}
                                    Općina: ${item.municipality}
                                    Entitet: ${item.entity}
                                    Kanton: ${item.canton}
                                    Ukupno izdato: ${item.total}
                                    Pogledaj više na: https://odp.gov.ba
                                """.trimIndent()

                                Share.shareData(context, shareText)
                            }
                        )
                    }
                }
            }
        }
    }
}
