package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcCardSelected
import com.example.ui.theme.ArcProgressBarBackground
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.IceBlue
import com.example.ui.theme.IceCyan
import com.example.util.DateUtils
import com.example.viewmodel.DayHistoryItem

@Composable
fun WeeklyBarChart(
    historyItems: List<DayHistoryItem>,
    selectedDate: String,
    onSelectDate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = DateUtils.todayDateString()

    val avgPercentage = if (historyItems.isNotEmpty()) {
        historyItems.sumOf { it.percentage } / historyItems.size
    } else 0

    Surface(
        modifier = modifier.testTag("weekly_bar_chart"),
        shape = RoundedCornerShape(20.dp),
        color = ArcCard,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "WOCHEN-VERLAUF",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceCyan,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Erfolgsquote der letzten 7 Tage",
                        fontSize = 13.sp,
                        color = ArcTextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(IceCyan.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Ø $avgPercentage%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bars Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                historyItems.forEach { item ->
                    val isSelected = item.date == selectedDate
                    val isToday = item.date == today
                    BarColumn(
                        item = item,
                        isSelected = isSelected,
                        isToday = isToday,
                        onClick = { onSelectDate(item.date) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(IceCyan)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
                Text(
                    text = "Ausgewählt",
                    fontSize = 11.sp,
                    color = ArcTextMuted,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DisciplinedGreen)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
                Text(
                    text = "100% geschafft",
                    fontSize = 11.sp,
                    color = ArcTextMuted
                )
            }
        }
    }
}

@Composable
private fun BarColumn(
    item: DayHistoryItem,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedHeightRatio by animateFloatAsState(
        targetValue = (item.percentage / 100f).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 600),
        label = "bar_height"
    )

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Percentage label above bar
        Text(
            text = if (item.percentage > 0) "${item.percentage}%" else "0%",
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = when {
                item.percentage == 100 -> DisciplinedGreen
                isSelected -> IceCyan
                item.percentage > 0 -> ArcTextSecondary
                else -> ArcTextMuted
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Bar Box with Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    if (isSelected) ArcCardSelected else Color.Transparent
                )
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
            ) {
                val barWidth = size.width.coerceAtMost(22.dp.toPx())
                val left = (size.width - barWidth) / 2f
                val totalH = size.height

                // Background track
                drawRoundRect(
                    color = ArcProgressBarBackground,
                    topLeft = Offset(left, 0f),
                    size = Size(barWidth, totalH),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Active Bar
                val barHeight = totalH * animatedHeightRatio
                if (barHeight > 0f) {
                    val brush = if (item.percentage == 100) {
                        Brush.verticalGradient(listOf(DisciplinedGreen, IceCyan))
                    } else if (isSelected) {
                        Brush.verticalGradient(listOf(IceCyan, IceBlue))
                    } else {
                        Brush.verticalGradient(listOf(IceBlue.copy(alpha = 0.85f), IceBlue.copy(alpha = 0.4f)))
                    }

                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset(left, totalH - barHeight),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Day label (e.g. Mo, Di, Mi)
        Text(
            text = item.dayLabel,
            fontSize = 11.sp,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) IceCyan else if (isToday) ArcTextPrimary else ArcTextMuted
        )
    }
}
