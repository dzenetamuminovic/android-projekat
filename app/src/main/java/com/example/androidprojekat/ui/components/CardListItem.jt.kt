package com.example.androidprojekat.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.androidprojekat.utils.Share

@Composable
fun CardListItem(
    context: Context,
    institution: String,
    municipality: String,
    entity: String,
    canton: String?,
    maleTotal: Int? = null,
    femaleTotal: Int? = null,
    total: Int,
    isFavourite: Boolean = false,
    showDelete: Boolean = false,
    onFavouriteToggle: ((Boolean) -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    viewMoreText: String,
    institutionLabel: String,
    municipalityLabel: String,
    entityLabel: String,
    cantonLabel: String,
    maleLabel: String? = null,
    femaleLabel: String? = null,
    totalLabel: String
) {
    val title = "$institutionLabel: $institution"
    val subtitle = "$municipalityLabel: $municipality"

    val expandedContent = buildString {
        appendLine("$entityLabel: $entity")
        if (!canton.isNullOrBlank()) appendLine("$cantonLabel: $canton")
        if (maleLabel != null && femaleLabel != null && maleTotal != null && femaleTotal != null) {
            appendLine("$maleLabel: $maleTotal")
            appendLine("$femaleLabel: $femaleTotal")
        }
        appendLine("$totalLabel: $total")
    }

    val shareText = buildString {
        appendLine(title)
        appendLine(subtitle)
        appendLine("$entityLabel: $entity")
        if (!canton.isNullOrBlank()) appendLine("$cantonLabel: $canton")
        if (maleLabel != null && maleTotal != null) appendLine("$maleLabel: $maleTotal")
        if (femaleLabel != null && femaleTotal != null) appendLine("$femaleLabel: $femaleTotal")
        appendLine("$totalLabel: $total")
        appendLine(viewMoreText)
    }

    CardItem(
        title = title,
        subtitle = subtitle,
        expandedContent = expandedContent.trim(),
        isFavouriteInitial = isFavourite,
        showDelete = showDelete,
        onFavouriteToggle = { newValue -> onFavouriteToggle?.invoke(newValue) },
        onDeleteClick = { onDeleteClick?.invoke() },
        onShareClick = { Share.shareData(context, shareText.trim()) }
    )
}
