package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_records")
data class DayRecord(
    @PrimaryKey
    val date: String, // Format: YYYY-MM-DD
    val waterOnly: Boolean = false,
    val noJunkFood: Boolean = false,
    val sleepHours: Boolean = false,
    val gymSession: Boolean = false,
    val runSession: Boolean = false,
    val tenKSteps: Boolean = false,
    val noPhoneMeals: Boolean = false,
    val outsideMinutes: Boolean = false,
    val beGrateful: Boolean = false,
    val gratitudeNote: String = "",
    val fiveWins: Boolean = false,
    val win1: String = "",
    val win2: String = "",
    val win3: String = "",
    val win4: String = "",
    val win5: String = "",
    val updatedAt: Long = System.currentTimeMillis()
) {
    val completedCount: Int
        get() {
            var count = 0
            if (waterOnly) count++
            if (noJunkFood) count++
            if (sleepHours) count++
            if (gymSession) count++
            if (runSession) count++
            if (tenKSteps) count++
            if (noPhoneMeals) count++
            if (outsideMinutes) count++
            if (beGrateful) count++
            if (fiveWins) count++
            return count
        }

    val percentage: Int
        get() = completedCount * 10
}
