package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.viewmodel.FavouritesViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel

@Composable
fun FavouritesScreen(universalViewModel: UniversalViewModel) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
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
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Institucija: ${item.institution}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Općina: ${item.municipality}",
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Entitet: ${item.entity}", color = MaterialTheme.colorScheme.onSurface)
                                Text("Kanton: ${item.canton}", color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Ukupno izdato: ${item.total}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Ukloni",
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                viewModel.removeFavourites(item)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
