package com.example.androidprojekat.data.local.expiredlcards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expired_dl_cards")
data class ExpiredDLCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entity: String,
    val canton: String?,
    val municipality: String,
    val institution: String,
    val dateUpdate: String,
    val maleTotal: Int,
    val femaleTotal: Int
)