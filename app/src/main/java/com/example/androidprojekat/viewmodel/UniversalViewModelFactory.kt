package com.example.androidprojekat.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojekat.repository.FavouritesRepository

class UniversalViewModelFactory(
    private val favouritesRepository: FavouritesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UniversalViewModel::class.java)) {
            return UniversalViewModel(favouritesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
