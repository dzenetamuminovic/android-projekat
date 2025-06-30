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

    init {
        fetchExpiredDLCards()
    }

    fun fetchExpiredDLCards() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = ExpiredDLCardRequest(
                    updateDate = "2025-06-03",
                    entityId = universalViewModel.selectedEntityIndexDL.value,
                    cantonId = universalViewModel.selectedCantonIndexDL.value
                )
                val result = repository.getExpiredDLCards(request)
                _expiredDLCards.value = result.result
            } catch (e: Exception) {
                _expiredDLCards.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
