package com.example.androidprojekat.viewmodel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel

class ExpiredDLCardsViewModelFactory(
    private val repository: ExpiredDLCardsRepository,
    private val universalViewModel: UniversalViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpiredDLCardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpiredDLCardsViewModel(repository, universalViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
