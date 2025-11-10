package com.example.caffeinetracker.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.caffeinetracker.presentation.viewmodel.CaffeineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: CaffeineViewModel) {
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
                    containerColor = Color(0xFF6750A4),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF6750A4),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Log")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Always show the graph – empty or filled
            CaffeineGraph(logs = logs)
        }
    }

    if (showDialog) {
        LogCaffeineDialog(
            onConfirm = { amount, time ->
                viewModel.addLog(amount, time)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}