package com.example.androidprojekat.ui.screens

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidprojekat.R
import com.example.androidprojekat.ui.theme.PrimaryTextBlue
import com.example.androidprojekat.ui.theme.StarYellow
import com.example.androidprojekat.data.preferences.LanguageDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity
    val coroutineScope = rememberCoroutineScope()

    val selectedLangCode by LanguageDataStore.getLanguage(context).collectAsState(initial = "bs")
    val selectedLanguage = if (selectedLangCode == "en") "English" else "Bosanski"

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown za izbor jezika
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable { expanded = true }
        ) {
            Text(
                text = selectedLanguage,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Bosanski") },
                    onClick = {
                        expanded = false
                        coroutineScope.launch {
                            if (selectedLangCode != "bs") {
                                LanguageDataStore.setLanguage(context, "bs")
                                activity?.recreate()
                            }
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        expanded = false
                        coroutineScope.launch {
                            if (selectedLangCode != "en") {
                                LanguageDataStore.setLanguage(context, "en")
                                activity?.recreate()
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.welcome),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = StarYellow, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                MenuBox(
                    text = stringResource(id = R.string.licnekarte),
                    icon = Icons.Default.Person
                ) {
                    navController.navigate("issued_cards")
                }

                Spacer(modifier = Modifier.height(16.dp))

                MenuBox(
                    text = stringResource(id = R.string.vozackedozvole),
                    icon = Icons.Default.DirectionsCar
                ) {
                    navController.navigate("expired_dl_cards")
                }

                Spacer(modifier = Modifier.height(16.dp))

                MenuBox(
                    text = stringResource(id = R.string.favourites),
                    icon = Icons.Default.Star
                ) {
                    navController.navigate("favourites")
                }

                MenuBox(
                    text = stringResource(id = R.string.statistika),
                    icon = Icons.Default.Leaderboard
                ) {
                    navController.navigate("statistics")
                }
            }
        }
    }
}

@Composable
fun MenuBox(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryTextBlue,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = PrimaryTextBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
