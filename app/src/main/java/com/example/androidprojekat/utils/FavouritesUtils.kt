package com.example.androidprojekat.utils

import com.example.androidprojekat.data.local.favourites.FavouritesItem

fun isItemInFavourites(
    favourites: List<FavouritesItem>,
    institution: String?,
    municipality: String?,
    entity: String?,
    canton: String?,
    total: Int
): FavouritesItem? {
    return favourites.find {
        (it.institution?.trim() ?: "").equals(institution?.trim() ?: "", ignoreCase = true) &&
                (it.entity?.trim() ?: "").equals(entity?.trim() ?: "", ignoreCase = true) &&
                (it.canton ?: "").trim().equals(canton?.trim() ?: "", ignoreCase = true) &&
                (it.municipality?.trim() ?: "").equals(municipality?.trim() ?: "", ignoreCase = true) &&
                it.total == total
    }
}
