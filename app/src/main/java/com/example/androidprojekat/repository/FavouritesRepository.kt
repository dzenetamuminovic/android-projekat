package com.example.androidprojekat.repository

import com.example.androidprojekat.data.local.FavouritesDao
import com.example.androidprojekat.data.local.FavouritesItem
import kotlinx.coroutines.flow.Flow

class FavouritesRepository(private val dao: FavouritesDao) {

    fun getAllFavourites(): Flow<List<FavouritesItem>> {
        return dao.getAllFavourites()
    }

    suspend fun insertFavourites(item: FavouritesItem) {
        dao.insertFavourites(item)
    }

    suspend fun deleteFavourites(item: FavouritesItem) {
        dao.deleteFavourites(item)
    }

    fun getFavouritesBySet(setId: Int): Flow<List<FavouritesItem>> {
        return dao.getFavouritesBySet(setId)
    }

}
