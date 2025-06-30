package com.example.androidprojekat.data.api.expireddlcards

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExpiredDLCardsApi {
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMTAwIiwibmJmIjoxNzUxMzAxMjAyLCJleHAiOjE3NTEzODc2MDIsImlhdCI6MTc1MTMwMTIwMn0.F1DwF6rojZyWwFIfNGPxpgi8IemkeVWhe7goDBIufhTLB60v2V6gfBsX2_wthNbSme6SBzdlmF4JkKLTdKsP0A"
    )
    @POST("ExpiredDLCards/list")
    suspend fun getExpiredDLCards(
        @Body request: ExpiredDLCardRequest
    ): ExpiredDLCardResponse
}