package com.example.androidprojekat.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojekat.repository.IssuedIdCardsRepository

class IssuedIdCardsViewModelFactory(
    private val repository: IssuedIdCardsRepository,
    private val universalViewModel: UniversalViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssuedIdCardsViewModel::class.java)) {
            return IssuedIdCardsViewModel(repository, universalViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}