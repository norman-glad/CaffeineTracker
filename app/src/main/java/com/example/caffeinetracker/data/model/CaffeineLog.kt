package com.example.caffeinetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caffeine_logs")
data class CaffeineLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val timestamp: Long
)