package com.example.androidprojekat.data.local.favourites

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<FavouritesItem>>

    @Query("SELECT * FROM favourites WHERE setId = :setId")
    fun getFavouritesBySet(setId: Int): Flow<List<FavouritesItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourites(item: FavouritesItem)

    @Delete
    suspend fun deleteFavourites(item: FavouritesItem)
}
