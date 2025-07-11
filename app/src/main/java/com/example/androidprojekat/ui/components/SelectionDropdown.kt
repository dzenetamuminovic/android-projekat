package com.example.androidprojekat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun SelectionDropdown(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (Int) -> Unit,
    enabled: Boolean = true
) {
    Box {
        OutlinedButton(
            onClick = { onExpandedChange(true) },
            enabled = enabled
        ) {
            Text(text = options[selectedIndex])
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(index)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
