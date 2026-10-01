package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class FriendEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val avatarEmoji: String = "🐺",
    val streak: Int = 0,
    val todayPercentage: Int = 0,
    val completedCount: Int = 0,
    val totalCount: Int = 10,
    val weeklyAverage: Int = 0,
    val statusMessage: String = "",
    val isCustomAdded: Boolean = false,
    val completedChallengeIds: String = "", // Comma-separated list of completed challenge IDs
    val lastNudge: String = ""
)
