package com.example.caffeinetracker.data.repository

import com.example.caffeinetracker.data.model.CaffeineLog
import kotlinx.coroutines.flow.Flow

interface CaffeineRepository {
    fun insertLog(log: CaffeineLog)
    fun getAllLogs(): Flow<List<CaffeineLog>>
}