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

    init {
        fetchIssuedIdCards()
    }

    fun fetchIssuedIdCards() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = IssuedIdCardRequest(
                    updateDate = "2025-06-03",
                    entityId = universalViewModel.selectedEntityIndex.value,
                    cantonId = universalViewModel.selectedCantonIndex.value
                )
                val result = repository.getIssuedIdCards(request)
                _issuedIdCards.value = result
            } catch (e: Exception) {
                _issuedIdCards.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
