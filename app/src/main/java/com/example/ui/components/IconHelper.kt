package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

object IconHelper {
    fun getIconForType(iconType: String): ImageVector {
        return when (iconType.lowercase()) {
            "water" -> Icons.Default.WaterDrop
            "food" -> Icons.Default.Restaurant
            "sleep" -> Icons.Default.Bedtime
            "gym" -> Icons.Default.FitnessCenter
            "run" -> Icons.Default.DirectionsRun
            "steps" -> Icons.Default.DirectionsWalk
            "phone" -> Icons.Default.PhoneDisabled
            "outside" -> Icons.Default.Park
            "gratitude" -> Icons.Default.Favorite
            "trophy" -> Icons.Default.EmojiEvents
            "book" -> Icons.Default.MenuBook
            "cold" -> Icons.Default.AcUnit
            "meditation" -> Icons.Default.SelfImprovement
            "alarm" -> Icons.Default.Alarm
            "clean" -> Icons.Default.CleaningServices
            else -> Icons.Default.CheckCircle
        }
    }

    val availableCustomIcons = listOf(
        Pair("book", "📚 Lesen"),
        Pair("cold", "❄️ Kalt duschen"),
        Pair("meditation", "🧘 Meditation"),
        Pair("alarm", "⏰ Früh aufstehen"),
        Pair("gym", "🏋️ Kraftsport"),
        Pair("run", "🏃 Cardio / Laufen"),
        Pair("food", "🥗 Ernährung"),
        Pair("water", "💧 Wasser"),
        Pair("clean", "🧹 Ordnung / Clean"),
        Pair("trophy", "🏆 Gewohnheit")
    )
}
