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

    init {
        fetchExpiredDLCards()
    }

    fun fetchExpiredDLCards(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true

            val hasInternet = repository.hasInternetConnection()

            if (hasInternet || forceRefresh) {
                try {
                    val request = ExpiredDLCardRequest(
                        updateDate = "2025-06-03",
                        entityId = universalViewModel.selectedEntityIndexDL.value,
                        cantonId = universalViewModel.selectedCantonIndexDL.value
                    )
                    val apiData = repository.getExpiredDLCards(request)
                    repository.clearLocalData()
                    repository.saveToLocal(apiData.result)
                    _expiredDLCards.value = apiData.result
                } catch (e: Exception) {
                    _expiredDLCards.value = repository.loadFromLocal().map {
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
                }
            } else {
                _expiredDLCards.value = repository.loadFromLocal().map {
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
            }

            _isLoading.value = false
        }
    }


    fun refreshExpiredDLCards() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchExpiredDLCards(forceRefresh = true)
            _isRefreshing.value = false
        }
    }
}
