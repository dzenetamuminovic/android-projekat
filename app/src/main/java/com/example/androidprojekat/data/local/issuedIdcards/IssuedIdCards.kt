package com.example.androidprojekat.data.local.issuedIdcards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issued_id_cards")
data class IssuedIdCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entity: String,
    val canton: String?,
    val municipality: String,
    val institution: String,
    val year: Int,
    val month: Int,
    val dateUpdate: String,
    val issuedFirstTimeMaleTotal: Int,
    val replacedMaleTotal: Int,
    val issuedFirstTimeFemaleTotal: Int,
    val replacedFemaleTotal: Int,
    val total: Int
)