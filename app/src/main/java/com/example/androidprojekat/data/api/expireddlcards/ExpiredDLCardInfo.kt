package com.example.androidprojekat.data.api.expireddlcards

data class ExpiredDLCardInfo(
    val entity: String,
    val canton: String,
    val municipality: String,
    val institution: String,
    val dateUpdate: String,
    val maleTotal: Int,
    val femaleTotal: Int
)