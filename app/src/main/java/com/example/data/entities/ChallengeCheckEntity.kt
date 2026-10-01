package com.example.data.entities

import androidx.room.Entity

@Entity(
    tableName = "challenge_checks",
    primaryKeys = ["date", "challengeId"]
)
data class ChallengeCheckEntity(
    val date: String, // YYYY-MM-DD
    val challengeId: String,
    val isCompleted: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
