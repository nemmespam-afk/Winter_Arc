package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entities.ChallengeCheckEntity
import com.example.data.entities.ChallengeEntity
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcProgressBarBackground
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.GoldArc
import com.example.ui.theme.IceBlue
import com.example.ui.theme.IceCyan
import com.example.viewmodel.DayHistoryItem
import com.example.viewmodel.OverallStats

@Composable
fun StatisticsView(
    overallStats: OverallStats,
    historyItems: List<DayHistoryItem>,
    challenges: List<ChallengeEntity>,
    allChecks: List<ChallengeCheckEntity>,
    selectedDate: String,
    onSelectDate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalLoggedDays = overallStats.totalLoggedDays.coerceAtLeast(1)

    // Calculate percentage per challenge
    val checksByChallenge = allChecks.groupBy { it.challengeId }
    val challengeStats = challenges.map { ch ->
        val checksForThis = checksByChallenge[ch.id].orEmpty()
        val count = checksForThis.count { it.isCompleted }
        val pct = (count * 100) / totalLoggedDays
        Triple(ch, count, pct)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("statistics_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "WINTER ARC ANALYSE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = IceCyan,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Deine Disziplin in Zahlen & Graphiken",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = ArcTextPrimary
            )
        }

        // Summary Metric Cards (2x2 Grid)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Gesamt-Quote",
                        value = "${overallStats.overallPercentage}%",
                        subtitle = "aller Aufgaben erfüllt",
                        icon = Icons.Default.QueryStats,
                        tint = IceCyan,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Aktueller Streak",
                        value = "${overallStats.streak} ${if (overallStats.streak == 1) "Tag" else "Tage"}",
                        subtitle = ">= 70% Disziplin",
                        icon = Icons.Default.LocalFireDepartment,
                        tint = GoldArc,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Perfekte Tage",
                        value = "${overallStats.perfectDays}",
                        subtitle = "Tage mit 100% Score",
                        icon = Icons.Default.EmojiEvents,
                        tint = DisciplinedGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Aufgaben erledigt",
                        value = "${overallStats.totalTasksCompleted}",
                        subtitle = "in ${overallStats.totalLoggedDays} getrackten Tagen",
                        icon = Icons.Default.Bolt,
                        tint = IceBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 7-Tage Verlaufs-Graphik
        item {
            WeeklyBarChart(
                historyItems = historyItems,
                selectedDate = selectedDate,
                onSelectDate = onSelectDate,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Breakdown per Challenge
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = ArcCard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "ERFOLGSQUOTE JE DISZIPLIN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceCyan,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Welche Herausforderungen meisterst du am besten?",
                        fontSize = 13.sp,
                        color = ArcTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    challengeStats.forEach { (ch, count, pct) ->
                        ChallengeProgressRow(
                            number = ch.orderIndex,
                            title = ch.title,
                            iconType = ch.iconType,
                            percentage = pct,
                            count = count,
                            totalDays = overallStats.totalLoggedDays
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        // Winter Arc Mindset Quote
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ArcNavyDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\"Der Winter Arc ist keine Phase, sondern ein Versprechen an dein zukünftiges Ich. Keine Ausreden, nur Resultate.\"",
                        fontSize = 13.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = ArcTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = ArcCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArcTextMuted,
                    letterSpacing = 0.5.sp
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(tint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = tint,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = ArcTextPrimary
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = ArcTextSecondary
            )
        }
    }
}

@Composable
private fun ChallengeProgressRow(
    number: Int,
    title: String,
    iconType: String,
    percentage: Int,
    count: Int,
    totalDays: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = String.format("%02d", number),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = IceCyan,
                    modifier = Modifier.width(24.dp)
                )
                Icon(
                    imageVector = IconHelper.getIconForType(iconType),
                    contentDescription = title,
                    tint = ArcTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextPrimary
                )
            }

            Text(
                text = "$percentage% ($count/$totalDays)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (percentage >= 80) DisciplinedGreen else if (percentage >= 50) IceBlue else ArcTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(ArcProgressBarBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = (percentage / 100f).coerceIn(0f, 1f))
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(
                        if (percentage >= 80) DisciplinedGreen else IceCyan
                    )
            )
        }
    }
}
