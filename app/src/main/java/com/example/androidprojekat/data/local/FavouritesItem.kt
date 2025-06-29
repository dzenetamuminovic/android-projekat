package com.example.androidprojekat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouritesItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val institution: String,
    val entity: String,
    val canton: String?,
    val municipality: String,
    val total: Int
)
