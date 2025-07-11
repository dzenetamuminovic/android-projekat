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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidprojekat.R
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.ui.components.CardListItem

@Composable
fun FavouritesScreen(
    universalViewModel: UniversalViewModel,
    navController: NavController,
    favouritesViewModel: FavouritesViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val favourites by favouritesViewModel.favourites.collectAsState()
    var filterText by remember { mutableStateOf("") }

    val filteredFavourites = favourites.filter {
        it.institution.contains(filterText, ignoreCase = true) ||
                it.municipality.contains(filterText, ignoreCase = true) ||
                it.entity.contains(filterText, ignoreCase = true) ||
                (it.canton ?: "").contains(filterText, ignoreCase = true) ||
                it.total.toString().contains(filterText)
    }

    val instLabel = stringResource(id = R.string.institution)
    val muniLabel = stringResource(id = R.string.municipality)
    val entLabel = stringResource(id = R.string.entity)
    val kantLabel = stringResource(id = R.string.canton)
    val totalLabel = stringResource(id = R.string.total_issued)
    val viewMore = stringResource(id = R.string.view_more)

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                favouritesRoute = "favourites",
                homeRoute = "home",
                statisticsRoute = "statistics"
            )
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
                text = stringResource(id = R.string.saved_data),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text(stringResource(id = R.string.search_saved)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredFavourites.isEmpty()) {
                Text(text = stringResource(id = R.string.noresults))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredFavourites) { item ->
                        CardListItem(
                            context = context,
                            institution = item.institution,
                            municipality = item.municipality,
                            entity = item.entity,
                            canton = item.canton,
                            total = item.total,
                            showDelete = true,
                            onDeleteClick = { favouritesViewModel.removeFavourites(item) },
                            viewMoreText = viewMore,
                            institutionLabel = instLabel,
                            municipalityLabel = muniLabel,
                            entityLabel = entLabel,
                            cantonLabel = kantLabel,
                            totalLabel = totalLabel
                        )
                    }
                }
            }
        }
    }
}
