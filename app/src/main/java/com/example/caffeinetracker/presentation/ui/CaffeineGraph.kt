package com.example.caffeinetracker.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.caffeinetracker.data.model.CaffeineLog
import com.example.caffeinetracker.ui.theme.GraphAxisDark
import com.example.caffeinetracker.ui.theme.GraphAxisLight
import com.example.caffeinetracker.ui.theme.GraphBackgroundDark
import com.example.caffeinetracker.ui.theme.GraphBackground
import com.example.caffeinetracker.ui.theme.GraphLineColor
import com.example.caffeinetracker.ui.theme.LocalDarkMode
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.pow

@Composable
fun CaffeineGraph(logs: List<CaffeineLog>) {
    val isEmpty = logs.isEmpty()
    val isDark = LocalDarkMode.current
    
    // Pre-compute colors based on theme
    val backgroundColor = if (isDark) MaterialTheme.colorScheme.background else androidx.compose.ui.graphics.Color.White
    val gridColor = if (isDark) GraphBackgroundDark else GraphBackground
    val axisColor = if (isDark) GraphAxisDark else GraphAxisLight
    val emptyLineColor = if (isDark) 0xFF555555.toInt() else 0xFFDDDDDD.toInt()
    val emptyTextColor = if (isDark) 0xFFAAAAAA.toInt() else 0xFF666666.toInt()

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setLabelCount(9, true)
                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String = "${value.toInt()}h"
                }
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                setDrawGridBackground(true)
            }
        },
        update = { chart ->
            // Update colors when theme changes
            chart.setBackgroundColor(backgroundColor.toArgb())
            chart.setGridBackgroundColor(gridColor.toArgb())
            chart.axisLeft.textColor = axisColor.toArgb()
            chart.xAxis.textColor = axisColor.toArgb()

            if (isEmpty) {
                chart.description.isEnabled = true
                chart.description.text = "Log caffeine to see the graph"
                chart.description.textSize = 16f
                chart.description.textColor = emptyTextColor

                val emptyEntries = listOf(Entry(-12f, 0f), Entry(12f, 0f))
                val emptySet = LineDataSet(emptyEntries, "").apply {
                    color = emptyLineColor
                    setDrawValues(false)
                    setDrawCircles(false)
                    setDrawFilled(false)
                }
                chart.data = LineData(emptySet)
            } else {
                chart.description.isEnabled = false

                val halfLifeMs = 5.0 * 60 * 60 * 1000
                val now = System.currentTimeMillis()
                val startTime = now - 12 * 60 * 60 * 1000
                val endTime = now + 12 * 60 * 60 * 1000
                val points = 200
                val step = (endTime - startTime).toDouble() / points

                val entries = mutableListOf<Entry>()
                for (i in 0..points) {
                    val t = startTime + (i * step).toLong()
                    var level = 0.0
                    for (log in logs) {
                        if (t >= log.timestamp) {
                            val hoursSince = (t - log.timestamp).toDouble() / halfLifeMs
                            level += log.amount * 0.5.pow(hoursSince)
                        }
                    }
                    val hoursFromNow = (t - now) / (1000.0 * 60 * 60)
                    entries.add(Entry(hoursFromNow.toFloat(), level.toFloat()))
                }

                val dataSet = LineDataSet(entries, "Caffeine Level").apply {
                    color = GraphLineColor.toArgb()
                    lineWidth = 2.5f
                    setDrawCircles(false)
                    setDrawValues(false)
                    setDrawFilled(true)
                    fillColor = GraphLineColor.copy(alpha = 0.3f).toArgb()
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
                chart.data = LineData(dataSet)
            }
            chart.animateX(300)
            chart.invalidate()
        },
        modifier = Modifier
            .height(400.dp)
            .fillMaxSize()
            .background(backgroundColor)
    )
}