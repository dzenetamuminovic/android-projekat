package com.example.androidprojekat.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("action/package_search")
    suspend fun searchPackages(
        @Query("q") query: String
    ): PackageSearchResponse
}