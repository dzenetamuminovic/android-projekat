package com.example.androidprojekat.repository

import android.content.Context
import com.example.androidprojekat.data.RetrofitInstance
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardRequest
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardInfo
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardResponse
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.data.local.issuedIdcards.IssuedIdCardEntity
import com.example.androidprojekat.utils.NetworkUtils

class IssuedIdCardsRepository(private val context: Context) {

    private val dao = DatabaseProvider.getDatabase(context).issuedIdCardsDao()

    suspend fun getIssuedIdCards(request: IssuedIdCardRequest): List<IssuedIdCardInfo> {
        val response = RetrofitInstance.issuedIdCardsApi.getIssuedIdCards(request)
        return response.result
    }

    suspend fun saveToLocal(data: List<IssuedIdCardInfo>) {
        val entities = data.map {
            IssuedIdCardEntity(
                entity = it.entity,
                canton = it.canton,
                municipality = it.municipality,
                institution = it.institution,
                year = it.year,
                month = it.month,
                dateUpdate = it.dateUpdate,
                issuedFirstTimeMaleTotal = it.issuedFirstTimeMaleTotal,
                replacedMaleTotal = it.replacedMaleTotal,
                issuedFirstTimeFemaleTotal = it.issuedFirstTimeFemaleTotal,
                replacedFemaleTotal = it.replacedFemaleTotal,
                total = it.total
            )
        }
        dao.insertAll(entities)
    }

    suspend fun loadFromLocal(): List<IssuedIdCardEntity> {
        return dao.getAll()
    }

    suspend fun clearLocalData() {
        dao.deleteAll()
    }

    fun hasInternetConnection(): Boolean {
        return NetworkUtils.hasInternetConnection(context)
    }
}