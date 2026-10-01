package com.example.data

import com.example.data.entities.ChallengeEntity
import com.example.data.entities.FriendEntity

object WinterArcDefaults {
    val defaultChallenges = listOf(
        ChallengeEntity(
            id = "default_1",
            title = "NUR WASSER",
            subtitle = "Nur reines Wasser & ungesüßter Tee. Kein Alkohol, keine Softdrinks oder Zuckergetränke.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "water",
            isCustom = false,
            orderIndex = 1,
            disciplineTip = "Optimale Hydration steigert Fokus, Fettstoffwechsel und Willenskraft."
        ),
        ChallengeEntity(
            id = "default_2",
            title = "KEIN JUNK FOOD",
            subtitle = "Clean Eating: Keine Süßigkeiten, Fast Food oder ultra-verarbeitete Snacks.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "food",
            isCustom = false,
            orderIndex = 2,
            disciplineTip = "Essen als Treibstoff für Höchstleistungen, nicht zur Belohnung."
        ),
        ChallengeEntity(
            id = "default_3",
            title = "7-8 STUNDEN SCHLAF",
            subtitle = "Feste Schlafenszeiten für tiefe nächtliche Regeneration und geistige Klarheit.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "sleep",
            isCustom = false,
            orderIndex = 3,
            disciplineTip = "Muskelaufbau und hormonelle Balance finden vor allem im Tiefschlaf statt."
        ),
        ChallengeEntity(
            id = "default_4",
            title = "4X GYM PRO WOCHE",
            subtitle = "Intensives Krafttraining im Gym. Wochenziel: 4 Einheiten.",
            cadence = "WEEKLY",
            cadenceLabel = "4x / Woche",
            targetCount = 4,
            iconType = "gym",
            isCustom = false,
            orderIndex = 4,
            disciplineTip = "Disziplin übertrifft Motivation. Jede Session zählt."
        ),
        ChallengeEntity(
            id = "default_5",
            title = "1X LAUFEN PRO WOCHE",
            subtitle = "Ausdauer-Lauf oder Jogging-Einheit für kardiovaskuläre Fitness.",
            cadence = "WEEKLY",
            cadenceLabel = "1x / Woche",
            targetCount = 1,
            iconType = "run",
            isCustom = false,
            orderIndex = 5,
            disciplineTip = "Laufen trainiert die Lunge und baut unerschütterliche Zähigkeit auf."
        ),
        ChallengeEntity(
            id = "default_6",
            title = "10.000 SCHRITTE TÄGLICH",
            subtitle = "Mindestens 10.000 Schritte am Tag für aktive Alltagsbewegung.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "steps",
            isCustom = false,
            orderIndex = 6,
            disciplineTip = "Konstante Bewegung verhindert Lethargie und verbrennt passiv Kalorien."
        ),
        ChallengeEntity(
            id = "default_7",
            title = "KEIN HANDY BEIM ESSEN",
            subtitle = "Achtsames Essen ohne Smartphone, Social Media oder TV.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "phone",
            isCustom = false,
            orderIndex = 7,
            disciplineTip = "Dopamin-Fasten beim Essen fördert Genuss, Sättigung und Achtsamkeit."
        ),
        ChallengeEntity(
            id = "default_8",
            title = "20 MIN DRAUSSEN TÄGLICH",
            subtitle = "Tageslicht und frische Luft tanken – bei jedem Wetter.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "outside",
            isCustom = false,
            orderIndex = 8,
            disciplineTip = "Natürliches Licht reguliert deinen zirkadianen Rhythmus und hebt die Stimmung."
        ),
        ChallengeEntity(
            id = "default_9",
            title = "DANKBARKEIT TÄGLICH",
            subtitle = "Innehalten und aufschreiben, wofür du heute dankbar bist.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "gratitude",
            isCustom = false,
            orderIndex = 9,
            disciplineTip = "Dankbarkeit nimmt dem Alltagsstress die Macht und schenkt innere Ruhe."
        ),
        ChallengeEntity(
            id = "default_10",
            title = "5 TÄGLICHE ERFOLGE",
            subtitle = "Dokumentiere 5 Siege des Tages – egal wie groß oder klein.",
            cadence = "DAILY",
            cadenceLabel = "Täglich",
            targetCount = 1,
            iconType = "trophy",
            isCustom = false,
            orderIndex = 10,
            disciplineTip = "Erfolge sichtbar machen konditioniert den Geist auf Gewinner-Modus."
        )
    )

    val defaultFriends = listOf(
        FriendEntity(
            id = "friend_sarah",
            name = "Sarah M.",
            avatarEmoji = "⚡",
            streak = 16,
            todayPercentage = 100,
            completedCount = 10,
            totalCount = 10,
            weeklyAverage = 94,
            statusMessage = "100% geschafft! 10k Schritte & Gym durch.",
            completedChallengeIds = "default_1,default_2,default_3,default_4,default_5,default_6,default_7,default_8,default_9,default_10"
        ),
        FriendEntity(
            id = "friend_leon",
            name = "Leon K.",
            avatarEmoji = "🐺",
            streak = 14,
            todayPercentage = 90,
            completedCount = 9,
            totalCount = 10,
            weeklyAverage = 88,
            statusMessage = "Gym um 06:00 Uhr erledigt. Kein Junk Food!",
            completedChallengeIds = "default_1,default_2,default_3,default_4,default_6,default_7,default_8,default_9,default_10"
        ),
        FriendEntity(
            id = "friend_max",
            name = "Max B.",
            avatarEmoji = "🦅",
            streak = 9,
            todayPercentage = 80,
            completedCount = 8,
            totalCount = 10,
            weeklyAverage = 82,
            statusMessage = "Schritte voll & nur Wasser getrunken.",
            completedChallengeIds = "default_1,default_2,default_3,default_6,default_7,default_8,default_9,default_10"
        ),
        FriendEntity(
            id = "friend_felix",
            name = "Felix W.",
            avatarEmoji = "🥊",
            streak = 6,
            todayPercentage = 70,
            completedCount = 7,
            totalCount = 10,
            weeklyAverage = 75,
            statusMessage = "Guter Start in den Tag, heute Abend noch Laufen!",
            completedChallengeIds = "default_1,default_2,default_3,default_6,default_7,default_8,default_9"
        )
    )
}
