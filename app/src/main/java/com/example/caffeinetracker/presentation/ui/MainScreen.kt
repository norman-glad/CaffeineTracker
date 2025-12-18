package com.example.caffeinetracker.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.outlined.DarkMode
//import androidx.compose.material.icons.outlined.LightMode
import com.example.caffeinetracker.presentation.viewmodel.CaffeineViewModel
import com.example.caffeinetracker.ui.theme.LocalDarkMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: CaffeineViewModel,
    isDarkMode: Boolean = false,
    onToggleDarkMode: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    val logs by viewModel.allLogs.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Caffeine Tracker",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onToggleDarkMode) {
                        Text(
                            text = if (isDarkMode) "☀️" else "🌙",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Log")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CaffeineGraph(logs = logs)
        }
    }

    // Animated dialog
    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(tween(200)) + scaleIn(tween(200), initialScale = 0.9f),
        exit = fadeOut(tween(150)) + scaleOut(tween(150), targetScale = 0.9f)
    ) {
        LogCaffeineDialog(
            onConfirm = { amount, time ->
                viewModel.addLog(amount, time)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}