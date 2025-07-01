package com.example.androidprojekat.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidprojekat.ui.theme.PrimaryTextBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = PrimaryTextBlue,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = it) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Nazad")
                }
            }
        },
        actions = {
            onRefreshClick?.let {
                IconButton(onClick = it) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Osvježi")
                }
            }
        }
    )
}
