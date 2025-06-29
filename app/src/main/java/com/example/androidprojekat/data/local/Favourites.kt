package com.example.androidprojekat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteItem(
    @PrimaryKey val id: String,
    val name: String,
    val description: String
)
