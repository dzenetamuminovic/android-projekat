package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardInfo
import com.example.androidprojekat.data.api.issuedidcards.IssuedIdCardRequest
import com.example.androidprojekat.repository.IssuedIdCardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IssuedIdCardsViewModel : ViewModel() {

    private val repository = IssuedIdCardsRepository()

    private val _issuedIdCards = MutableStateFlow<List<IssuedIdCardInfo>>(emptyList())
    val issuedIdCards: StateFlow<List<IssuedIdCardInfo>> = _issuedIdCards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val entityOptions = listOf("Entiteti", "Federacija BiH", "Republika Srpska", "Brčko Distrikt")
    val cantonOptions = listOf(
        "Kantoni",
        "UNSKO-SANSKI KANTON",
        "POSAVSKI KANTON",
        "TUZLANSKI KANTON",
        "ZENIČKO-DOBOJSKI KANTON",
        "BOSANSKO-PODRINJSKI KANTON",
        "SREDNJOBOSANSKI KANTON",
        "HERCEGOVAČKO-NERETVANSKI KANTON",
        "ZAPADNOHERCEGOVAČKI KANTON",
        "KANTON 10",
        "SARAJEVSKI KANTON"
    )

    var selectedEntityIndex = MutableStateFlow(0)
    var selectedCantonIndex = MutableStateFlow(0)

    init {
        fetchIssuedIdCards()
    }

    fun isCantonDisabled(): Boolean {
        val selectedEntity = entityOptions[selectedEntityIndex.value]
        return selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"
    }

    fun updateSelections(entity: Int, canton: Int = 0) {
        selectedEntityIndex.value = entity
        selectedCantonIndex.value = canton
        fetchIssuedIdCards()
    }

    fun fetchIssuedIdCards() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = IssuedIdCardRequest(
                    updateDate = "2025-06-03",
                    entityId = selectedEntityIndex.value,
                    cantonId = selectedCantonIndex.value,
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
