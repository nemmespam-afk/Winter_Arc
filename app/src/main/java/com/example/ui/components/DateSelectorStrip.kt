package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DayRecord
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcCardSelected
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.IceCyan
import com.example.util.DateUtils

@Composable
fun DateSelectorStrip(
    selectedDate: String,
    records: List<DayRecord>,
    onSelectDate: (String) -> Unit,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weekDays = DateUtils.getWeekDays(selectedDate)
    val recordMap = records.associateBy { it.date }
    val today = DateUtils.todayDateString()
    val isTodaySelected = selectedDate == today

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("date_selector_strip")
    ) {
        // Date navigation bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPreviousDay,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("prev_day_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Vorheriger Tag",
                    tint = IceCyan
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onToday() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = DateUtils.formatDateGerman(selectedDate),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArcTextPrimary
                    )
                    if (!isTodaySelected) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(IceCyan.copy(alpha = 0.2f))
                                .clickable { onToday() }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "HEUTE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = IceCyan
                            )
                        }
                    }
                }
                Text(
                    text = DateUtils.formatFullDateGerman(selectedDate),
                    fontSize = 11.sp,
                    color = ArcTextSecondary
                )
            }

            IconButton(
                onClick = onNextDay,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("next_day_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Nächster Tag",
                    tint = IceCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Weekday selector pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekDays.forEach { date ->
                val isSelected = date == selectedDate
                val isTodayItem = date == today
                val record = recordMap[date]
                val pct = record?.percentage ?: 0
                val dayShort = DateUtils.getDayOfWeekShort(date)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when {
                                isSelected -> IceCyan.copy(alpha = 0.15f)
                                isTodayItem -> ArcCardSelected
                                else -> ArcCard
                            }
                        )
                        .border(
                            width = 1.dp,
                            color = when {
                                isSelected -> IceCyan
                                isTodayItem -> IceCyan.copy(alpha = 0.4f)
                                else -> ArcBorder
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectDate(date) }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dayShort,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) IceCyan else ArcTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Indicator dot or %
                    if (pct > 0) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pct == 100) DisciplinedGreen else IceCyan.copy(alpha = 0.3f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${pct / 10}",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = if (pct == 100) ArcNavyDark else IceCyan
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) IceCyan else ArcTextMuted.copy(alpha = 0.4f))
                        )
                    }
                }
            }
        }
    }
}
