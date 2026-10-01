package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_notes")
data class DayNoteEntity(
    @PrimaryKey
    val date: String, // YYYY-MM-DD
    val gratitudeNote: String = "",
    val win1: String = "",
    val win2: String = "",
    val win3: String = "",
    val win4: String = "",
    val win5: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
