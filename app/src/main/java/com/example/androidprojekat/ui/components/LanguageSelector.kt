package com.example.androidprojekat.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable

@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { expanded = true }
    ) {
        Text(
            text = if (selectedLanguage == "en") "English" else "Bosanski",
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
                    onLanguageChange("bs")
                }
            )
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    expanded = false
                    onLanguageChange("en")
                }
            )
        }
    }
}
