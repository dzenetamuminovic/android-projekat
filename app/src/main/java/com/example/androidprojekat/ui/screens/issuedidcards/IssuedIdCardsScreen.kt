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

@Composable
fun IssuedIdCardsScreen(viewModel: IssuedIdCardsViewModel = viewModel()) {
    val issuedIdCards by viewModel.issuedIdCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val entityIndex by viewModel.selectedEntityIndex.collectAsState()
    val cantonIndex by viewModel.selectedCantonIndex.collectAsState()

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
                OutlinedButton(onClick = { cantonExpanded = true }) {
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
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Institucija: ${item.institution}", style = MaterialTheme.typography.titleMedium)
                            Text("Entitet: ${item.entity}")
                            Text("Kanton: ${item.canton}")
                            Text("Općina: ${item.municipality}")
                            Spacer(Modifier.height(8.dp))
                            Text("Ukupno izdato: ${item.total}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
