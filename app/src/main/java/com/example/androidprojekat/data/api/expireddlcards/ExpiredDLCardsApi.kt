package com.example.androidprojekat.data.api.expireddlcards

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExpiredDLCardsApi {
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMTAwIiwibmJmIjoxNzUyMjQ3MDIzLCJleHAiOjE3NTIzMzM0MjMsImlhdCI6MTc1MjI0NzAyM30.IkLUmxik_v1vxP-brW1fOEt4KHsv-nOBifQ8tLHTaYJa_Uv4ZFgT5tshH3UNnR0_RPnuJ9u6Wk_BQ52npiO5kg"
    )
    @POST("ExpiredDLCards/list")
    suspend fun getExpiredDLCards(
        @Body request: ExpiredDLCardRequest
    ): ExpiredDLCardResponse
}