package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojekat.data.local.FavouritesItem
import com.example.androidprojekat.repository.FavouritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: FavouritesRepository) : ViewModel() {

    private val _favourites = MutableStateFlow<List<FavouritesItem>>(emptyList())
    val favourites: StateFlow<List<FavouritesItem>> = _favourites

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            repository.getAllFavourites().collect {
                _favourites.value = it
            }
        }
    }

    fun addFavourites(item: FavouritesItem) {
        viewModelScope.launch {
            repository.insertFavourites(item)
        }
    }

    fun removeFavourites(item: FavouritesItem) {
        viewModelScope.launch {
            repository.deleteFavourites(item)
        }
    }

    fun loadFavouritesBySet(setId: Int) {
        viewModelScope.launch {
            repository.getFavouritesBySet(setId).collect {
                _favourites.value = it
            }
        }
    }

}
