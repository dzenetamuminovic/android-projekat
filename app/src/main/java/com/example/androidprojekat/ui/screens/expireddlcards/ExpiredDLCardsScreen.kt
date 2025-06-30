package com.example.androidprojekat.ui.screens.expireddlcards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.data.local.FavouritesItem
import com.example.androidprojekat.ui.components.CardItem
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.utils.Share
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest

@Composable
fun ExpiredDLCardsScreen(
    viewModel: ExpiredDLCardsViewModel = viewModel(),
    universalViewModel: UniversalViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val expiredDLCards by universalViewModel.expiredDLCards.collectAsState()
    val isLoading by universalViewModel.isLoading.collectAsState()
    val entityIndex by universalViewModel.selectedEntityIndexDL.collectAsState()
    val cantonIndex by universalViewModel.selectedCantonIndexDL.collectAsState()
    val selectedEntity = universalViewModel.entityOptions[entityIndex]
    val isCantonDisabled = selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"
    val favourites by universalViewModel.favourites.collectAsState()

    var entityExpanded by remember { mutableStateOf(false) }
    var cantonExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(entityIndex, cantonIndex) {
        val request = ExpiredDLCardRequest(
            updateDate = "2025-06-03",
            entityId = entityIndex,
            cantonId = cantonIndex
        )
        universalViewModel.fetchExpiredDLCards(request)
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, favouritesRoute = "favourites", homeRoute = "home")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Istekle vozačke dozvole",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.padding(end = 8.dp)) {
                    OutlinedButton(onClick = { entityExpanded = true }) {
                        Text(universalViewModel.entityOptions[entityIndex])
                    }
                    DropdownMenu(entityExpanded, { entityExpanded = false }) {
                        universalViewModel.entityOptions.forEachIndexed { index, option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = {
                                universalViewModel.updateSelectionsDL(index, cantonIndex)
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
                        Text(universalViewModel.cantonOptions[cantonIndex])
                    }
                    DropdownMenu(cantonExpanded, { cantonExpanded = false }) {
                        universalViewModel.cantonOptions.forEachIndexed { index, option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = {
                                universalViewModel.updateSelectionsDL(entityIndex, index)
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
            } else if (expiredDLCards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nema dostupnih podataka.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(expiredDLCards) { item ->
                        val total = item.maleTotal + item.femaleTotal
                        val existingFavourite = favourites.find {
                            (it.institution?.trim() ?: "").equals(item.institution?.trim() ?: "", ignoreCase = true) &&
                                    (it.entity?.trim() ?: "").equals(item.entity?.trim() ?: "", ignoreCase = true) &&
                                    (it.canton ?: "").trim().equals(item.canton?.trim() ?: "", ignoreCase = true) &&
                                    (it.municipality?.trim() ?: "").equals(item.municipality?.trim() ?: "", ignoreCase = true) &&
                                    it.total == total
                        }

                        CardItem(
                            title = "Institucija: ${item.institution ?: ""}",
                            subtitle = "Općina: ${item.municipality ?: ""}",
                            expandedContent = """
                                Entitet: ${item.entity ?: ""}
                                Kanton: ${item.canton ?: ""}
                                Muškarci: ${item.maleTotal}
                                Žene: ${item.femaleTotal}
                                Ukupno isteklih dozvola: $total
                            """.trimIndent(),
                            isFavouriteInitial = existingFavourite != null,
                            showDelete = false,
                            onFavouriteToggle = { newState ->
                                if (newState && existingFavourite == null) {
                                    universalViewModel.addToFavourites(
                                        FavouritesItem(
                                            institution = item.institution ?: "",
                                            entity = item.entity ?: "",
                                            canton = item.canton,
                                            municipality = item.municipality ?: "",
                                            total = total,
                                            setId = 2
                                        )
                                    )
                                } else if (!newState && existingFavourite != null) {
                                    universalViewModel.removeFromFavourites(existingFavourite)
                                }
                            },
                            onShareClick = {
                                val shareText = """
                                    Institucija: ${item.institution ?: ""}
                                    Općina: ${item.municipality ?: ""}
                                    Entitet: ${item.entity ?: ""}
                                    Kanton: ${item.canton ?: ""}
                                    Muškarci: ${item.maleTotal}
                                    Žene: ${item.femaleTotal}
                                    Ukupno isteklih dozvola: $total
                                    Pogledaj više na: https://odp.gov.ba/istekle-vozacke
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
