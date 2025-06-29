package com.example.androidprojekat.data


data class PackageData(
    val id: String,
    val title: String,
    val notes: String? ,
    val organization: OrganizationData?
)

data class OrganizationData(
    val title: String
)