package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcProgressBarBackground
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.IceBlue
import com.example.ui.theme.IceCyan
import com.example.ui.theme.IceGlow

@Composable
fun CircularProgressGraphic(
    percentage: Int,
    completedCount: Int,
    totalCount: Int = 10,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentage / 100f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progress_anim"
    )

    val progressGradient = Brush.sweepGradient(
        listOf(
            IceCyan,
            IceBlue,
            if (percentage >= 100) DisciplinedGreen else IceGlow,
            IceCyan
        )
    )

    Surface(
        modifier = modifier.testTag("circular_progress_card"),
        shape = RoundedCornerShape(20.dp),
        color = ArcCard,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .testTag("progress_gauge"),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 14.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset(
                        (size.width - diameter) / 2f,
                        (size.height - diameter) / 2f
                    )
                    val arcSize = Size(diameter, diameter)

                    // Background Track
                    drawArc(
                        color = ArcProgressBarBackground,
                        startAngle = 140f,
                        sweepAngle = 260f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Foreground Active Arc
                    if (animatedProgress > 0f) {
                        drawArc(
                            brush = progressGradient,
                            startAngle = 140f,
                            sweepAngle = 260f * animatedProgress,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    // Ticks for each challenge around the gauge
                    val radius = diameter / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val ticks = totalCount.coerceAtLeast(1)
                    for (i in 0..ticks) {
                        val angleDeg = 140f + (260f * (i / ticks.toFloat()))
                        val angleRad = Math.toRadians(angleDeg.toDouble())
                        val tickInnerRadius = radius - strokeWidth / 2f - 4.dp.toPx()
                        val tickOuterRadius = radius + strokeWidth / 2f + 4.dp.toPx()

                        val startX = (center.x + tickInnerRadius * Math.cos(angleRad)).toFloat()
                        val startY = (center.y + tickInnerRadius * Math.sin(angleRad)).toFloat()
                        val endX = (center.x + tickOuterRadius * Math.cos(angleRad)).toFloat()
                        val endY = (center.y + tickOuterRadius * Math.sin(angleRad)).toFloat()

                        val isPassed = i <= completedCount
                        val tickColor = if (isPassed) IceCyan.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.12f)

                        drawLine(
                            color = tickColor,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Center Label
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$percentage%",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.SansSerif,
                        color = if (percentage == 100) DisciplinedGreen else ArcTextPrimary
                    )
                    Text(
                        text = "$completedCount von $totalCount erfüllt",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = ArcTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status Badge
            val (badgeText, badgeColor, badgeBg) = when {
                percentage == 100 -> Triple("❄️ 100% PERFEKTION", DisciplinedGreen, DisciplinedGreen.copy(alpha = 0.18f))
                percentage >= 80 -> Triple("🔥 WINTER ARC BEAST MODE", IceCyan, IceCyan.copy(alpha = 0.15f))
                percentage >= 50 -> Triple("⚡ AUF KURS – WEITER SO", IceBlue, IceBlue.copy(alpha = 0.15f))
                percentage > 0 -> Triple("💪 DISZIPLIN STARTEN", ArcTextSecondary, Color.White.copy(alpha = 0.08f))
                else -> Triple("❄️ HEUTIGES ZIEL: 10/10", ArcTextMuted, Color.White.copy(alpha = 0.05f))
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(badgeBg)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = badgeText,
                    color = badgeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
