package com.example.androidprojekat.data.api.expireddlcards

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExpiredDLCardsApi {
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMTAwIiwibmJmIjoxNzUxMzk1MDQ5LCJleHAiOjE3NTE0ODE0NDksImlhdCI6MTc1MTM5NTA0OX0.QzzwLvlqMT-k08PwW4EEwi-0z9gJ6MAXqT2lmsGQE0kiXdAhefWD6dcMHY4Gn-7MGmHnazAiBapv-g6IR2wHgw"
    )
    @POST("ExpiredDLCards/list")
    suspend fun getExpiredDLCards(
        @Body request: ExpiredDLCardRequest
    ): ExpiredDLCardResponse
}