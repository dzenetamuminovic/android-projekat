package com.example.androidprojekat.repository

import android.content.Context
import com.example.androidprojekat.data.RetrofitInstance
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardInfo
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.data.local.expiredlcards.ExpiredDLCardEntity
import com.example.androidprojekat.utils.NetworkUtils

class ExpiredDLCardsRepository(private val context: Context) {

    private val dao = DatabaseProvider.getDatabase(context).expiredDLCardsDao()

    suspend fun getExpiredDLCards(request: ExpiredDLCardRequest): List<ExpiredDLCardInfo> {
        val response = RetrofitInstance.expiredDLCardsApi.getExpiredDLCards(request)
        return response.result
    }

    suspend fun saveToLocal(data: List<ExpiredDLCardInfo>) {
        val entities = data.map {
            ExpiredDLCardEntity(
                entity = it.entity,
                canton = it.canton,
                municipality = it.municipality,
                institution = it.institution,
                dateUpdate = it.dateUpdate,
                maleTotal = it.maleTotal,
                femaleTotal = it.femaleTotal
            )
        }
        dao.insertAll(entities)
    }

    suspend fun loadFromLocal(): List<ExpiredDLCardEntity> {
        return dao.getAll()
    }

    suspend fun clearLocalData() {
        dao.deleteAll()
    }

    fun hasInternetConnection(): Boolean {
        return NetworkUtils.hasInternetConnection(context)
    }
}
