package com.example.caffeinetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.caffeinetracker.data.model.CaffeineLog
import kotlinx.coroutines.flow.Flow

@Dao
interface CaffeineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(log: CaffeineLog)

    @Query("SELECT * FROM caffeine_logs ORDER BY timestamp ASC")
    fun getAllLogs(): Flow<List<CaffeineLog>>
}