package com.example.androidprojekat.ui.screens.expireddlcards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidprojekat.R
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.ui.components.CardItem
import com.example.androidprojekat.utils.Share
import com.example.androidprojekat.data.local.favourites.FavouritesItem

@Composable
fun ExpiredDLCardsScreen(
    viewModel: ExpiredDLCardsViewModel = viewModel(),
    universalViewModel: UniversalViewModel,
    favouritesViewModel: FavouritesViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val expiredDLCards by viewModel.expiredDLCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val entityIndex by universalViewModel.selectedEntityIndexDL.collectAsState()
    val cantonIndex by universalViewModel.selectedCantonIndexDL.collectAsState()
    val selectedEntity = universalViewModel.entityOptions[entityIndex]
    val isCantonDisabled = selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"
    val favourites by favouritesViewModel.favourites.collectAsState()

    var entityExpanded by remember { mutableStateOf(false) }
    var cantonExpanded by remember { mutableStateOf(false) }

    val titleText = stringResource(id = R.string.vozackedozvole)
    val noResultsText = stringResource(id = R.string.noresults)
    val institutionText = stringResource(id = R.string.institution)
    val municipalityText = stringResource(id = R.string.municipality)
    val entityText = stringResource(id = R.string.entity)
    val cantonText = stringResource(id = R.string.canton)
    val maleText = stringResource(id = R.string.male)
    val femaleText = stringResource(id = R.string.female)
    val viewMoreText = stringResource(id = R.string.view_more_dl)

    LaunchedEffect(entityIndex, cantonIndex) {
        viewModel.fetchExpiredDLCards()
    }

    LaunchedEffect(Unit) {
        favouritesViewModel.loadFavouritesBySet(2)
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, favouritesRoute = "favourites", homeRoute = "home", statisticsRoute = "statistics")
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
                text = titleText,
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
                    DropdownMenu(
                        expanded = entityExpanded,
                        onDismissRequest = { entityExpanded = false }
                    ) {
                        universalViewModel.entityOptions.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    universalViewModel.updateSelectionsDL(index, cantonIndex)
                                    entityExpanded = false
                                }
                            )
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
                    DropdownMenu(
                        expanded = cantonExpanded,
                        onDismissRequest = { cantonExpanded = false }
                    ) {
                        universalViewModel.cantonOptions.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    universalViewModel.updateSelectionsDL(entityIndex, index)
                                    cantonExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (isLoading && expiredDLCards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (expiredDLCards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = noResultsText)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(expiredDLCards) { item ->
                        val total = item.maleTotal + item.femaleTotal
                        val expiredText = stringResource(id = R.string.expired_total, total)

                        val existingFavourite = favourites.find {
                            (it.institution?.trim() ?: "").equals(item.institution?.trim() ?: "", ignoreCase = true) &&
                                    (it.entity?.trim() ?: "").equals(item.entity?.trim() ?: "", ignoreCase = true) &&
                                    (it.canton ?: "").trim().equals(item.canton?.trim() ?: "", ignoreCase = true) &&
                                    (it.municipality?.trim() ?: "").equals(item.municipality?.trim() ?: "", ignoreCase = true) &&
                                    it.total == total
                        }

                        CardItem(
                            title = "$institutionText: ${item.institution ?: ""}",
                            subtitle = "$municipalityText: ${item.municipality ?: ""}",
                            expandedContent = """
                                $entityText: ${item.entity ?: ""}
                                $cantonText: ${item.canton ?: ""}
                                $maleText: ${item.maleTotal}
                                $femaleText: ${item.femaleTotal}
                                $expiredText
                            """.trimIndent(),
                            isFavouriteInitial = existingFavourite != null,
                            showDelete = false,
                            onFavouriteToggle = { newState ->
                                if (newState && existingFavourite == null) {
                                    favouritesViewModel.addFavourites(
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
                                    favouritesViewModel.removeFavourites(existingFavourite)
                                }
                            },
                            onShareClick = {
                                val shareText = """
                                    $institutionText: ${item.institution ?: ""}
                                    $municipalityText: ${item.municipality ?: ""}
                                    $entityText: ${item.entity ?: ""}
                                    $cantonText: ${item.canton ?: ""}
                                    $maleText: ${item.maleTotal}
                                    $femaleText: ${item.femaleTotal}
                                    $expiredText
                                    $viewMoreText
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
