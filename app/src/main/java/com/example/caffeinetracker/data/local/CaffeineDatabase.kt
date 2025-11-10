package com.example.caffeinetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.caffeinetracker.data.local.dao.CaffeineDao
import com.example.caffeinetracker.data.model.CaffeineLog

@Database(entities = [CaffeineLog::class], version = 1, exportSchema = false)
abstract class CaffeineDatabase : RoomDatabase() {
    abstract fun caffeineDao(): CaffeineDao

    companion object {
        @Volatile
        private var INSTANCE: CaffeineDatabase? = null

        fun getDatabase(context: Context): CaffeineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaffeineDatabase::class.java,
                    "caffeine_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}