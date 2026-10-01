package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

enum class ChallengeCadence {
    DAILY,
    WEEKLY
}

data class WinterArcChallenge(
    val id: Int,
    val title: String,
    val subtitle: String,
    val cadence: ChallengeCadence,
    val cadenceLabel: String,
    val icon: ImageVector,
    val disciplineTip: String
)

object ChallengeCatalog {
    val challenges = listOf(
        WinterArcChallenge(
            id = 1,
            title = "WATER ONLY",
            subtitle = "Nur reines Wasser & ungesüßter Tee. 0% Softdrinks, Alkohol oder Zuckergetränke.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.WaterDrop,
            disciplineTip = "Hydration steigert Konzentration, Fettstoffwechsel und Willenskraft."
        ),
        WinterArcChallenge(
            id = 2,
            title = "NO JUNK FOOD",
            subtitle = "Clean Eating: Kein Fast Food, keine Süßigkeiten, keine hochverarbeiteten Snacks.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.Restaurant,
            disciplineTip = "Du isst für Energie und Performance, nicht aus Langeweile."
        ),
        WinterArcChallenge(
            id = 3,
            title = "7-8 HOURS OF SLEEP",
            subtitle = "Mindestens 7 bis 8 Stunden tiefen, erholsamen Schlaf priorisieren.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.Bedtime,
            disciplineTip = "Im Schlaf wachsen Muskeln und dein Nervensystem regeneriert."
        ),
        WinterArcChallenge(
            id = 4,
            title = "GYM 4X PER WEEK",
            subtitle = "Intensives Krafttraining im Gym. Wochenziel: 4 Einheiten.",
            cadence = ChallengeCadence.WEEKLY,
            cadenceLabel = "4x / Woche",
            icon = Icons.Default.FitnessCenter,
            disciplineTip = "Konstanz schlägt Motivation. Kein ausgelassenes Workout."
        ),
        WinterArcChallenge(
            id = 5,
            title = "RUN 1X PER WEEK",
            subtitle = "Ausdauerlauf / Cardio-Session im Freien oder Laufband. Ziel: 1x.",
            cadence = ChallengeCadence.WEEKLY,
            cadenceLabel = "1x / Woche",
            icon = Icons.Default.DirectionsRun,
            disciplineTip = "Kardiovaskuläre Ausdauer ist das Fundament mentaler Zähigkeit."
        ),
        WinterArcChallenge(
            id = 6,
            title = "10K STEPS DAILY",
            subtitle = "Mindestens 10.000 Schritte am Tag zurücklegen (NEAT erhöhen).",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.DirectionsWalk,
            disciplineTip = "Jeder Schritt summiert sich. Nutze Spaziergänge für mentale Klarheit."
        ),
        WinterArcChallenge(
            id = 7,
            title = "NO PHONE DURING MEALS",
            subtitle = "Bildschirmfreies Essen: Kein Smartphone, Social Media oder TV beim Essen.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.PhoneDisabled,
            disciplineTip = "Dopamin-Detox am Tisch verbessert Verdauung und Gegenwärtigkeit."
        ),
        WinterArcChallenge(
            id = 8,
            title = "20 MIN OUTSIDE DAILY",
            subtitle = "Mindestens 20 Minuten im Freien bei natürlichem Tageslicht verbringen.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.Park,
            disciplineTip = "Sonnenlicht synchronisiert deinen circadianen Rhythmus."
        ),
        WinterArcChallenge(
            id = 9,
            title = "BE GRATEFUL DAILY",
            subtitle = "Dankbarkeit praktizieren: Halte inne und reflektiere worüber du dankbar bist.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.Favorite,
            disciplineTip = "Eine dankbare Haltung verwandelt Druck in unzerstörbaren inneren Frieden."
        ),
        WinterArcChallenge(
            id = 10,
            title = "TRACK 5 DAILY WINS",
            subtitle = "Dokumentiere 5 Siege des Tages – egal wie groß oder klein.",
            cadence = ChallengeCadence.DAILY,
            cadenceLabel = "Täglich",
            icon = Icons.Default.EmojiEvents,
            disciplineTip = "Erfolge sichtbar machen konditioniert dein Gehirn auf stetiges Gewinnen."
        )
    )
}
