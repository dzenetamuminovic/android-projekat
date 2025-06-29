package com.example.androidprojekat.data

data class PackageSearchResponse(
    val result: Result
)

data class Result(
    val count: Int,
    val results: List<PackageData>
)