package com.example.caffeinetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.caffeinetracker.data.local.CaffeineDatabase
import com.example.caffeinetracker.data.repository.CaffeineRepositoryImpl
import com.example.caffeinetracker.presentation.ui.MainScreen
import com.example.caffeinetracker.presentation.viewmodel.CaffeineViewModel
import com.example.caffeinetracker.ui.theme.CaffeineTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = CaffeineDatabase.getDatabase(this)
        val repository = CaffeineRepositoryImpl(db.caffeineDao())
        val viewModel: CaffeineViewModel by viewModels { CaffeineViewModelFactory(repository) }

        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDarkMode by rememberSaveable { mutableStateOf(systemDark) }

            CaffeineTrackerTheme(darkTheme = isDarkMode) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(
                        viewModel = viewModel,
                        isDarkMode = isDarkMode,
                        onToggleDarkMode = { isDarkMode = !isDarkMode }
                    )
                }
            }
        }
    }
}