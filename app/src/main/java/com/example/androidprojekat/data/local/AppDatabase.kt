package com.example.androidprojekat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidprojekat.data.local.expiredlcards.ExpiredDLCardsDao
import com.example.androidprojekat.data.local.expiredlcards.ExpiredDLCardEntity
import com.example.androidprojekat.data.local.issuedIdcards.IssuedIdCardEntity
import com.example.androidprojekat.data.local.issuedIdcards.IssuedIdCardsDao
import com.example.androidprojekat.data.local.favourites.FavouritesDao
import com.example.androidprojekat.data.local.favourites.FavouritesItem


@Database(
    entities = [
        FavouritesItem::class,
        IssuedIdCardEntity::class,
        ExpiredDLCardEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun issuedIdCardsDao(): IssuedIdCardsDao
    abstract fun expiredDLCardsDao(): ExpiredDLCardsDao
}
