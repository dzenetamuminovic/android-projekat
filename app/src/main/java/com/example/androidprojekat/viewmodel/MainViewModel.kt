package com.example.androidprojekat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidprojekat.data.PackageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _selectedPackage = MutableStateFlow<PackageData?>(null)
    val selectedPackage: StateFlow<PackageData?> = _selectedPackage

    fun setSelectedPackage(pkg: PackageData) {
        _selectedPackage.value = pkg
    }
}
