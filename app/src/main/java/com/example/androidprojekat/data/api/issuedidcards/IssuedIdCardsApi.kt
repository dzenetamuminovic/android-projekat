package com.example.androidprojekat.data.api.issuedidcards

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IssuedIdCardsApi {

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMTAwIiwibmJmIjoxNzUxMjA1NjUxLCJleHAiOjE3NTEyOTIwNTEsImlhdCI6MTc1MTIwNTY1MX0.RjSEQ3BYoiXrfXmv0qobuW8LgomfEIrxA5FQ_ndGNnCbrZ-5xf71uIhmLBbxLlIo6IuJEI8CHFgjrHj-S829uA"
    )
    @POST("IssuedIDCards/list")
    suspend fun getIssuedIdCards(
        @Body request: IssuedIdCardRequest
    ): IssuedIdCardResponse

}
