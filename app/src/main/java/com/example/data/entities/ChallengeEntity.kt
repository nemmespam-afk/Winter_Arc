package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val cadence: String, // "DAILY" or "WEEKLY"
    val cadenceLabel: String,
    val targetCount: Int = 1,
    val iconType: String = "default",
    val isCustom: Boolean = false,
    val isActive: Boolean = true,
    val orderIndex: Int = 0,
    val disciplineTip: String = ""
)
