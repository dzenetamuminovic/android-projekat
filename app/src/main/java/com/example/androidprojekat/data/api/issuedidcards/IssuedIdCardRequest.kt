package com.example.androidprojekat.data.api.issuedidcards

data class IssuedIdCardRequest(
    val updateDate: String = "2023-11-07",
    val entityId: Int = 0,
    val cantonId: Int = 0,
    val municipalityId: Int = 0
)