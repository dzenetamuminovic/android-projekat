package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.local.FavouritesItem
import com.example.androidprojekat.repository.FavouritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UniversalViewModel(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<FavouritesItem>>(emptyList())
    val favourites: StateFlow<List<FavouritesItem>> = _favourites

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

    val selectedEntityIndex = MutableStateFlow(0)
    val selectedCantonIndex = MutableStateFlow(0)

    init {
        loadFavourites()
    }

    fun isCantonDisabled(): Boolean {
        val selectedEntity = entityOptions[selectedEntityIndex.value]
        return selectedEntity == "Republika Srpska" || selectedEntity == "Brčko Distrikt"
    }

    fun updateSelections(entity: Int, canton: Int = 0) {
        selectedEntityIndex.value = entity
        selectedCantonIndex.value = canton
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            favouritesRepository.getAllFavourites().collect {
                _favourites.value = it
            }
        }
    }

    fun addToFavourites(item: FavouritesItem) {
        viewModelScope.launch {
            favouritesRepository.insertFavourites(item)
        }
    }

    fun removeFromFavourites(item: FavouritesItem) {
        viewModelScope.launch {
            favouritesRepository.deleteFavourites(item)
        }
    }
}