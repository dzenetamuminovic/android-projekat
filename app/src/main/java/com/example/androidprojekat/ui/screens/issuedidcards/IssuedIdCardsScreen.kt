package com.example.androidprojekat.ui.screens.issuedidcards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.data.FavouritesRepository
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.data.local.FavouritesItem
import com.example.androidprojekat.ui.theme.FavouriteBackground

@Composable
fun IssuedIdCardsScreen(viewModel: IssuedIdCardsViewModel = viewModel(), navController: NavController) {
    val issuedIdCards by viewModel.issuedIdCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val entityIndex by viewModel.selectedEntityIndex.collectAsState()
    val cantonIndex by viewModel.selectedCantonIndex.collectAsState()
    val selectedEntity = viewModel.entityOptions[entityIndex]
    val isCantonDisabled = selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"

    val context = LocalContext.current
    val favouritesDao = DatabaseProvider.getDatabase(context).favouritesDao()
    val favouritesRepository = FavouritesRepository(favouritesDao)
    val favouritesViewModel = remember { FavouritesViewModel(favouritesRepository) }
    val favourites by favouritesViewModel.favourites.collectAsState()

    var entityExpanded by remember { mutableStateOf(false) }
    var cantonExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.padding(end = 8.dp)) {
                OutlinedButton(onClick = { entityExpanded = true }) {
                    Text(viewModel.entityOptions[entityIndex])
                }
                DropdownMenu(entityExpanded, { entityExpanded = false }) {
                    viewModel.entityOptions.forEachIndexed { index, option ->
                        DropdownMenuItem(text = { Text(option) }, onClick = {
                            viewModel.updateSelections(index, cantonIndex)
                            entityExpanded = false
                        })
                    }
                }
            }

            Box {
                OutlinedButton(
                    onClick = { cantonExpanded = true },
                    enabled = entityIndex == 0 || !isCantonDisabled
                ) {
                    Text(viewModel.cantonOptions[cantonIndex])
                }
                DropdownMenu(cantonExpanded, { cantonExpanded = false }) {
                    viewModel.cantonOptions.forEachIndexed { index, option ->
                        DropdownMenuItem(text = { Text(option) }, onClick = {
                            viewModel.updateSelections(entityIndex, index)
                            cantonExpanded = false
                        })
                    }
                }
            }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (issuedIdCards.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nema dostupnih podataka.")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(issuedIdCards) { item ->

                    val existingFavourite = favourites.find {
                        (it.institution ?: "").trim().equals(item.institution?.trim() ?: "", ignoreCase = true) &&
                                (it.entity ?: "").trim().equals(item.entity?.trim() ?: "", ignoreCase = true) &&
                                (it.canton ?: "").trim().equals(item.canton?.trim() ?: "", ignoreCase = true) &&
                                (it.municipality ?: "").trim().equals(item.municipality?.trim() ?: "", ignoreCase = true) &&
                                it.total == item.total
                    }

                    var isFavourite by remember { mutableStateOf(existingFavourite != null) }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isFavourite) FavouriteBackground else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Institucija: ${item.institution}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )

                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Zvjezdica",
                                    tint = if (isFavourite) Color.Yellow else Color.Gray,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            if (isFavourite && existingFavourite != null) {
                                                favouritesViewModel.removeFavourites(existingFavourite)
                                                isFavourite = false
                                            } else if (!isFavourite) {
                                                favouritesViewModel.addFavourites(
                                                    FavouritesItem(
                                                        institution = item.institution,
                                                        entity = item.entity,
                                                        canton = item.canton,
                                                        municipality = item.municipality,
                                                        total = item.total
                                                    )
                                                )
                                                isFavourite = true
                                            }
                                        }
                                )
                            }

                            Text(
                                "Entitet: ${item.entity}",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Kanton: ${item.canton}",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Općina: ${item.municipality}",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Ukupno izdato: ${item.total}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
