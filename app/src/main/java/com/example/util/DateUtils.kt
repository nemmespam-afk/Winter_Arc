package com.example.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val shortGermanFormat = SimpleDateFormat("EEE, d. MMM", Locale.GERMAN)
    private val fullGermanFormat = SimpleDateFormat("EEEE, d. MMMM yyyy", Locale.GERMAN)
    private val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.GERMAN)

    fun todayDateString(): String {
        return isoFormat.format(Date())
    }

    fun parseDate(dateStr: String): Date {
        return try {
            isoFormat.parse(dateStr) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    fun formatDateGerman(dateStr: String): String {
        return try {
            val date = parseDate(dateStr)
            val today = todayDateString()
            if (dateStr == today) {
                "Heute (${shortGermanFormat.format(date)})"
            } else {
                shortGermanFormat.format(date)
            }
        } catch (e: Exception) {
            dateStr
        }
    }

    fun formatFullDateGerman(dateStr: String): String {
        return try {
            val date = parseDate(dateStr)
            fullGermanFormat.format(date)
        } catch (e: Exception) {
            dateStr
        }
    }

    fun getDayOfWeekShort(dateStr: String): String {
        return try {
            val date = parseDate(dateStr)
            dayOfWeekFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun getOffsetDay(dateStr: String, offsetDays: Int): String {
        val cal = Calendar.getInstance()
        cal.time = parseDate(dateStr)
        cal.add(Calendar.DAY_OF_YEAR, offsetDays)
        return isoFormat.format(cal.time)
    }

    fun getLast7Days(untilDateStr: String = todayDateString()): List<String> {
        val list = mutableListOf<String>()
        for (i in 6 downTo 0) {
            list.add(getOffsetDay(untilDateStr, -i))
        }
        return list
    }

    fun getWeekDays(referenceDateStr: String): List<String> {
        val cal = Calendar.getInstance()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.time = parseDate(referenceDateStr)

        // Find Monday of this week
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val daysToMonday = if (dayOfWeek == Calendar.SUNDAY) -6 else Calendar.MONDAY - dayOfWeek
        cal.add(Calendar.DAY_OF_YEAR, daysToMonday)

        val list = mutableListOf<String>()
        for (i in 0 until 7) {
            list.add(isoFormat.format(cal.time))
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        return list
    }
}
