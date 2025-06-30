package com.example.androidprojekat.data

data class DatasetListRequest(
    val languageId: Int = 4
)

data class DatasetListResponse(
    val success: Boolean,
    val result: List<DatasetItem>
)

data class DatasetItem(
    val id: Int,
    val name: String,
    val description: String
)
