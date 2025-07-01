package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidprojekat.repository.FavouritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.androidprojekat.data.local.favourites.FavouritesItem
import com.example.androidprojekat.repository.ExpiredDLCardsRepository

class UniversalViewModel() : ViewModel() {

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
}
