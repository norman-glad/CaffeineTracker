package com.example.caffeinetracker.presentation.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
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
        title = { Text("Log Caffeine") },
        text = {
            Column {
                Text("caffeine :")
                TextField(value = amountText, onValueChange = { amountText = it })

                Row {
                    Button(onClick = { amountText = "25" }) { Text("25 mg") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { amountText = "50" }) { Text("50 mg") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { amountText = "100" }) { Text("100 mg") }
                }

                Text("time :")
                Text("$date $time")

                Row {
                    Button(onClick = {
                        DatePickerDialog(context, { _, y, m, d ->
                            calendar.set(y, m, d)
                            date = dateFormat.format(calendar.time)
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
                    }) { Text("Date") }

                    Spacer(Modifier.width(8.dp))

                    Button(onClick = {
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
            Button(onClick = {
                val amount = amountText.toDoubleOrNull() ?: 0.0
                onConfirm(amount, calendar.timeInMillis)
            }) { Text("Log") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}