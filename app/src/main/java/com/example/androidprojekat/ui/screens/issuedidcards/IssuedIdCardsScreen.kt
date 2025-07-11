package com.example.androidprojekat.ui.screens.issuedidcards

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
import com.example.androidprojekat.viewmodel.IssuedIdCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.ui.components.CardItem
import com.example.androidprojekat.utils.Share
import com.example.androidprojekat.data.local.favourites.FavouritesItem

@Composable
fun IssuedIdCardsScreen(
    viewModel: IssuedIdCardsViewModel = viewModel(),
    universalViewModel: UniversalViewModel,
    favouritesViewModel: FavouritesViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val issuedIdCards by viewModel.issuedIdCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val entityIndex by universalViewModel.selectedEntityIndexID.collectAsState()
    val cantonIndex by universalViewModel.selectedCantonIndexID.collectAsState()
    val selectedEntity = universalViewModel.entityOptions[entityIndex]
    val isCantonDisabled = selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"
    val favourites by favouritesViewModel.favourites.collectAsState()

    var entityExpanded by remember { mutableStateOf(false) }
    var cantonExpanded by remember { mutableStateOf(false) }

    val titleText = stringResource(id = R.string.licnekarte)
    val noResultsText = stringResource(id = R.string.noresults)
    val institutionText = stringResource(id = R.string.institution)
    val municipalityText = stringResource(id = R.string.municipality)
    val entityText = stringResource(id = R.string.entity)
    val cantonText = stringResource(id = R.string.canton)
    val totalIssuedText = stringResource(id = R.string.total_issued)
    val viewMoreText = stringResource(id = R.string.view_more)

    LaunchedEffect(entityIndex, cantonIndex) {
        viewModel.fetchIssuedIdCards()
    }

    LaunchedEffect(Unit) {
        favouritesViewModel.loadFavouritesBySet(1)
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
                                    universalViewModel.updateSelectionsID(index, cantonIndex)
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
                                    universalViewModel.updateSelectionsID(entityIndex, index)
                                    cantonExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (isLoading && issuedIdCards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (issuedIdCards.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = noResultsText)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(issuedIdCards) { item ->
                        val existingFavourite = favourites.find {
                            (it.institution?.trim() ?: "").equals(item.institution?.trim() ?: "", ignoreCase = true) &&
                                    (it.entity?.trim() ?: "").equals(item.entity?.trim() ?: "", ignoreCase = true) &&
                                    (it.canton ?: "").trim().equals(item.canton?.trim() ?: "", ignoreCase = true) &&
                                    (it.municipality?.trim() ?: "").equals(item.municipality?.trim() ?: "", ignoreCase = true) &&
                                    it.total == item.total
                        }

                        CardItem(
                            title = "$institutionText: ${item.institution ?: ""}",
                            subtitle = "$municipalityText: ${item.municipality ?: ""}",
                            expandedContent = """
                                $entityText: ${item.entity ?: ""}
                                $cantonText: ${item.canton ?: ""}
                                $totalIssuedText: ${item.total}
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
                                            total = item.total,
                                            setId = 1
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
                                    $totalIssuedText: ${item.total}
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
