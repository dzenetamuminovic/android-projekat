package com.example.androidprojekat.viewmodel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import com.example.androidprojekat.repository.FavouritesRepository
import com.example.androidprojekat.viewmodel.UniversalViewModel

class UniversalViewModelFactory(
    private val favouritesRepository: FavouritesRepository,
    private val expiredDLCardsRepository: ExpiredDLCardsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UniversalViewModel::class.java)) {
            return UniversalViewModel(favouritesRepository, expiredDLCardsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
