package com.example.caffeinetracker.presentation.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LogCaffeineDialog(
    onConfirm: (Double, Long) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    var amountText by remember { mutableStateOf("100") }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    var date by remember { mutableStateOf(dateFormat.format(calendar.time)) }
    var time by remember { mutableStateOf(timeFormat.format(calendar.time)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        title = { 
            Text(
                "Log Caffeine",
                style = MaterialTheme.typography.headlineSmall
            ) 
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Caffeine (mg):", style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(
                    value = amountText, 
                    onValueChange = { amountText = it },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = { amountText = "25" }) { Text("25 mg") }
                    FilledTonalButton(onClick = { amountText = "50" }) { Text("50 mg") }
                    FilledTonalButton(onClick = { amountText = "100" }) { Text("100 mg") }
                }

                Spacer(Modifier.height(8.dp))
                Text("Time:", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "$date  $time",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        DatePickerDialog(context, { _, y, m, d ->
                            calendar.set(y, m, d)
                            date = dateFormat.format(calendar.time)
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
                    }) { Text("Date") }

                    OutlinedButton(onClick = {
                        TimePickerDialog(context, { _, h, min ->
                            calendar.set(Calendar.HOUR_OF_DAY, h)
                            calendar.set(Calendar.MINUTE, min)
                            time = timeFormat.format(calendar.time)
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
                    }) { Text("Time") }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    onConfirm(amount, calendar.timeInMillis)
                },
                shape = RoundedCornerShape(12.dp)
            ) { Text("Log") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}