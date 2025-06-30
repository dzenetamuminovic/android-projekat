package com.example.androidprojekat.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardsApi
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardsApi

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://odp.iddeea.gov.ba:8096/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val issuedIdCardsApi: IssuedIdCardsApi by lazy {
        retrofit.create(IssuedIdCardsApi::class.java)
    }

    val expiredDLCardsApi: ExpiredDLCardsApi by lazy {
        retrofit.create(ExpiredDLCardsApi::class.java)
    }
}
