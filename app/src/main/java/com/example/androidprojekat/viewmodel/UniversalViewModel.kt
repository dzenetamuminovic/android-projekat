package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardInfo
import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardRequest
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.data.local.favourites.FavouritesItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UniversalViewModel(
    private val favouritesRepository: FavouritesRepository,
    private val expiredDLCardsRepository: ExpiredDLCardsRepository
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<FavouritesItem>>(emptyList())
    val favourites: StateFlow<List<FavouritesItem>> = _favourites

    private val _expiredDLCards = MutableStateFlow<List<ExpiredDLCardInfo>>(emptyList())
    val expiredDLCards: StateFlow<List<ExpiredDLCardInfo>> = _expiredDLCards

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

    // Filteri za Issued ID kartice
    val selectedEntityIndexID = MutableStateFlow(0)
    val selectedCantonIndexID = MutableStateFlow(0)

    // Filteri za DL kartice
    val selectedEntityIndexDL = MutableStateFlow(0)
    val selectedCantonIndexDL = MutableStateFlow(0)

    fun updateSelectionsID(entity: Int, canton: Int = 0) {
        selectedEntityIndexID.value = entity
        selectedCantonIndexID.value = canton
    }

    fun updateSelectionsDL(entity: Int, canton: Int = 0) {
        selectedEntityIndexDL.value = entity
        selectedCantonIndexDL.value = canton
    }

    fun fetchExpiredDLCards(request: ExpiredDLCardRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = expiredDLCardsRepository.getExpiredDLCards(request)
                _expiredDLCards.value = response.result
            } catch (e: Exception) {
                _expiredDLCards.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

}
