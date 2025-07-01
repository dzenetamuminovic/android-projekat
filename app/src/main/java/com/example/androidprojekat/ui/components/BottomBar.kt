package com.example.androidprojekat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidprojekat.ui.theme.PrimaryTextBlue
import com.example.androidprojekat.ui.theme.TopBarColor

@Composable
fun BottomBar(navController: NavController, favouritesRoute: String, homeRoute: String, statisticsRoute: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(TopBarColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(onClick = { navController.navigate(statisticsRoute) }) {
            Icon(
                imageVector = Icons.Filled.Leaderboard,
                contentDescription = "Statistika",
                tint = PrimaryTextBlue
            )
        }

        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .height(24.dp)
                .width(1.dp)
        )

        IconButton(onClick = { navController.navigate(homeRoute) }) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Početna",
                tint = PrimaryTextBlue
            )
        }

        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .height(24.dp)
                .width(1.dp)
        )

        IconButton(onClick = { navController.navigate(favouritesRoute) }) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Omiljeno",
                tint = PrimaryTextBlue
            )
        }
    }
}
