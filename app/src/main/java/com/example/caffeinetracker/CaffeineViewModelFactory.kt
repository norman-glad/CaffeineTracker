package com.example.caffeinetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.caffeinetracker.data.repository.CaffeineRepository
import com.example.caffeinetracker.presentation.viewmodel.CaffeineViewModel

class CaffeineViewModelFactory(private val repository: CaffeineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaffeineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaffeineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}