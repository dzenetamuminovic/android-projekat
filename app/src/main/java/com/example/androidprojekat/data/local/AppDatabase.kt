package com.example.androidprojekat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouritesItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}
