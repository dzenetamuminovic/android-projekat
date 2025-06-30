package com.example.androidprojekat.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardInfo
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardResponse
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardsApi
import com.example.androidprojekat.data.local.DatabaseProvider
import com.example.androidprojekat.data.local.expiredlcards.ExpiredDLCardEntity

class ExpiredDLCardsRepository(
    private val api: ExpiredDLCardsApi,
    private val context: Context
) {

    private val dao = DatabaseProvider.getDatabase(context).expiredDLCardsDao()

    suspend fun getExpiredDLCards(request: ExpiredDLCardRequest): ExpiredDLCardResponse {
        return api.getExpiredDLCards(request)
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
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
