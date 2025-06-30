package com.example.androidprojekat.data.local.issuedIdcards

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IssuedIdCardsDao {

    @Insert
    suspend fun insertAll(items: List<IssuedIdCardEntity>)

    @Query("SELECT * FROM issued_id_cards")
    suspend fun getAll(): List<IssuedIdCardEntity>

    @Query("DELETE FROM issued_id_cards")
    suspend fun deleteAll()
}