package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardInfo
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardRequest
import com.example.androidprojekat.repository.IssuedIdCardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IssuedIdCardsViewModel(
    private val repository: IssuedIdCardsRepository,
    private val universalViewModel: UniversalViewModel
) : ViewModel() {

    private val _issuedIdCards = MutableStateFlow<List<IssuedIdCardInfo>>(emptyList())
    val issuedIdCards: StateFlow<List<IssuedIdCardInfo>> = _issuedIdCards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        fetchIssuedIdCards()
    }

    fun fetchIssuedIdCards(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true

            println("Pokrećem dohvaćanje IDS podataka...")
            println("Entity: ${universalViewModel.selectedEntityIndexID.value}, Canton: ${universalViewModel.selectedCantonIndexID.value}")

            val hasInternet = repository.hasInternetConnection()
            println("Internet dostupan: $hasInternet")

            if (hasInternet || forceRefresh) {
                try {
                    val request = IssuedIdCardRequest(
                        updateDate = "2025-06-03",
                        entityId = universalViewModel.selectedEntityIndexID.value,
                        cantonId = universalViewModel.selectedCantonIndexID.value
                    )
                    val apiData = repository.getIssuedIdCards(request)
                    println("Broj podataka sa API-ja: ${apiData.size}")

                    repository.clearLocalData()
                    repository.saveToLocal(apiData)
                    _issuedIdCards.value = apiData
                } catch (e: Exception) {
                    println("Greška pri dohvaćanju sa API-ja: ${e.message}")
                    _issuedIdCards.value = repository.loadFromLocal().map {
                        IssuedIdCardInfo(
                            entity = it.entity,
                            canton = it.canton ?: "",
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
                }
            } else {
                println("Nema interneta — učitavam iz lokalne baze.")
                _issuedIdCards.value = repository.loadFromLocal().map {
                    IssuedIdCardInfo(
                        entity = it.entity,
                        canton = it.canton ?: "",
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
            }

            _isLoading.value = false
        }
    }

    fun refreshIssuedIdCards() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchIssuedIdCards(forceRefresh = true)
            _isRefreshing.value = false
        }
    }
}
