package com.example.androidprojekat.data.local.expiredlcards


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpiredDLCardsDao {

    @Insert
    suspend fun insertAll(items: List<ExpiredDLCardEntity>)

    @Query("SELECT * FROM expired_dl_cards")
    suspend fun getAll(): List<ExpiredDLCardEntity>

    @Query("DELETE FROM expired_dl_cards")
    suspend fun deleteAll()
}