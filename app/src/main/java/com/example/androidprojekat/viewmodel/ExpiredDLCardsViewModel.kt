package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardInfo
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpiredDLCardsViewModel(
    private val repository: ExpiredDLCardsRepository,
    private val universalViewModel: UniversalViewModel
) : ViewModel() {

    private val _expiredDLCards = MutableStateFlow<List<ExpiredDLCardInfo>>(emptyList())
    val expiredDLCards: StateFlow<List<ExpiredDLCardInfo>> = _expiredDLCards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _dataSource = MutableStateFlow("Nepoznat izvor")
    val dataSource: StateFlow<String> = _dataSource

    init {
        fetchExpiredDLCards()
    }

    fun fetchExpiredDLCards(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true

            println("Provjeravam internet konekciju...")
            val hasInternet = repository.hasInternetConnection()
            println("Internet dostupan: $hasInternet")

            if (hasInternet || forceRefresh) {
                try {
                    println("Pokušavam dohvatiti podatke SA INTERNETA...")
                    val request = ExpiredDLCardRequest(
                        updateDate = "2025-06-03",
                        entityId = universalViewModel.selectedEntityIndexDL.value,
                        cantonId = universalViewModel.selectedCantonIndexDL.value
                    )
                    val apiData = repository.getExpiredDLCards(request)
                    println("Podaci uspješno dohvaćeni sa interneta. Ukupno: ${apiData.result.size}")

                    repository.clearLocalData()
                    repository.saveToLocal(apiData.result)
                    _expiredDLCards.value = apiData.result
                    _dataSource.value = "Podaci sa interneta"
                } catch (e: Exception) {
                    println("Greška pri dohvaćanju sa interneta: ${e.message}")
                    loadFromLocalAndSet()
                }
            } else {
                println("Nema interneta — učitavam podatke iz baze...")
                loadFromLocalAndSet()
            }

            _isLoading.value = false
        }
    }

    private suspend fun loadFromLocalAndSet() {
        val localData = repository.loadFromLocal().map {
            ExpiredDLCardInfo(
                entity = it.entity,
                canton = it.canton ?: "",
                municipality = it.municipality,
                institution = it.institution,
                dateUpdate = it.dateUpdate,
                maleTotal = it.maleTotal,
                femaleTotal = it.femaleTotal
            )
        }
        println("Podaci iz baze učitani. Ukupno: ${localData.size}")
        _expiredDLCards.value = localData
        _dataSource.value = "Podaci iz baze"
    }

    fun refreshExpiredDLCards() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val hasInternet = repository.hasInternetConnection()
            println("Refresh — ima interneta: $hasInternet")
            fetchExpiredDLCards(forceRefresh = hasInternet)
            _isRefreshing.value = false
        }
    }
}
