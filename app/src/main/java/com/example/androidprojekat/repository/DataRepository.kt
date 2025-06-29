package com.example.androidprojekat.repository

import com.example.androidprojekat.data.PackageSearchResponse
import com.example.androidprojekat.data.RetrofitInstance
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardRequest
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardResponse

class DataRepository{

    suspend fun getIssuedIdCards(request: IssuedIdCardRequest): IssuedIdCardResponse {
        return RetrofitInstance.issuedIdCardsApi.getIssuedIdCards(request)
    }
}