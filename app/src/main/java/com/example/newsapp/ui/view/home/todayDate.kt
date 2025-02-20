package com.example.newsapp.ui.view.home

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val todayDate: String get() {
    val sdfDay = SimpleDateFormat("dd", Locale.getDefault())
    val sdfMonthYear = SimpleDateFormat("yyyy-MM", Locale.getDefault())

    val currentDate = Date()
    val day = sdfDay.format(currentDate)

    return "2025-01-$day"
}
val yesterdayDate: String
    get() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }