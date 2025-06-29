package com.example.androidprojekat.repository

import com.example.androidprojekat.data.RetrofitInstance
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardInfo
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardRequest
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardsApi

class IssuedIdCardsRepository {

    suspend fun getIssuedIdCards(request: IssuedIdCardRequest): List<IssuedIdCardInfo> {
        val response = RetrofitInstance.issuedIdCardsApi.getIssuedIdCards(request)
        return response.result
    }
}
