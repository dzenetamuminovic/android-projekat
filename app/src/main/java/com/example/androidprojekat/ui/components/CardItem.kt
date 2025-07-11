package com.example.androidprojekat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidprojekat.ui.theme.FavouriteBackground
import androidx.compose.ui.res.stringResource
import com.example.androidprojekat.R

@Composable
fun CardItem(
    title: String,
    subtitle: String,
    expandedContent: String,
    isFavouriteInitial: Boolean = false,
    showDelete: Boolean = false,
    onFavouriteToggle: (Boolean) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onShareClick: () -> Unit
) {
    var isFavourite by remember { mutableStateOf(isFavouriteInitial) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (isFavourite && !showDelete) FavouriteBackground else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(expandedContent, style = MaterialTheme.typography.bodySmall)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showDelete) {
                        IconButton(onClick = onDeleteClick) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.star),
                            tint = if (isFavourite) Color.Yellow else Color.Gray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    isFavourite = !isFavourite
                                    onFavouriteToggle(isFavourite)
                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = onShareClick) {
                        Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                    }
                }
            }
        }
    }
}
