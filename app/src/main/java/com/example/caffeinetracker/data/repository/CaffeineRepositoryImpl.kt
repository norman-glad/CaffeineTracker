package com.example.caffeinetracker.data.repository

import com.example.caffeinetracker.data.local.dao.CaffeineDao
import com.example.caffeinetracker.data.model.CaffeineLog
import kotlinx.coroutines.flow.Flow



class CaffeineRepositoryImpl(private val dao: CaffeineDao) : CaffeineRepository {
    override fun insertLog(log: CaffeineLog) {
        dao.insertLog(log)
    }

    override fun getAllLogs(): Flow<List<CaffeineLog>> {
        return dao.getAllLogs()
    }
}