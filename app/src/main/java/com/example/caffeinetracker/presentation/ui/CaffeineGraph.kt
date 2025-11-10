package com.example.caffeinetracker.presentation.ui

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.caffeinetracker.data.model.CaffeineLog
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

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                // Base styling
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setLabelCount(9, true)
                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String = "${value.toInt()}h"
                }
                axisRight.isEnabled = false
                axisLeft.textColor = parseColor("#000000")
                xAxis.textColor = parseColor("#000000")
                description.isEnabled = false
                legend.isEnabled = false
                setBackgroundColor(parseColor("#FFFFFF"))
                setDrawGridBackground(true)
                setGridBackgroundColor(parseColor("#F0F0F0"))
            }
        },
        update = { chart ->  // ← THIS IS THE MAGIC LINE THAT WAS MISSING
            if (isEmpty) {
                chart.description.isEnabled = true
                chart.description.text = "Log caffeine to see the graph"
                chart.description.textSize = 16f
                chart.description.textColor = parseColor("#666666")

                val emptyEntries = listOf(Entry(-12f, 0f), Entry(12f, 0f))
                val emptySet = LineDataSet(emptyEntries, "").apply {
                    color = parseColor("#DDDDDD")
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
                    color = androidx.compose.ui.graphics.Color(0xFF6750A4).toArgb()
                    lineWidth = 2.5f
                    setDrawCircles(false)
                    setDrawValues(false)
                    setDrawFilled(true)
                    fillColor = androidx.compose.ui.graphics.Color(0xFF6750A4).copy(alpha = 0.3f).toArgb()
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
                chart.data = LineData(dataSet)
            }
            chart.invalidate()  // ← Force redraw
        },
        modifier = Modifier
            .height(400.dp)
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White)
    )
}