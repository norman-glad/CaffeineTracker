package com.example.caffeinetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caffeinetracker.data.model.CaffeineLog
import com.example.caffeinetracker.data.repository.CaffeineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CaffeineViewModel(private val repository: CaffeineRepository) : ViewModel() {
    val allLogs: Flow<List<CaffeineLog>> = repository.getAllLogs()

    fun addLog(amount: Double, timestamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertLog(CaffeineLog(amount = amount, timestamp = timestamp))
        }
    }
}