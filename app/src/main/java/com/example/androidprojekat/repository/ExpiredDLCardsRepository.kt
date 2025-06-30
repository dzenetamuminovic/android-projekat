package com.example.androidprojekat.repository

import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardResponse
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardsApi

class ExpiredDLCardsRepository(private val api: ExpiredDLCardsApi) {

    suspend fun getExpiredDLCards(request: ExpiredDLCardRequest): ExpiredDLCardResponse {
        return api.getExpiredDLCards(request)
    }
}