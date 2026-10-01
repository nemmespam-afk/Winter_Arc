package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.DayNoteEntity
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcCardSelected
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.GoldArc
import com.example.ui.theme.IceBlue
import com.example.ui.theme.IceCyan

@Composable
fun ChallengeItemCard(
    challenge: ChallengeEntity,
    isCompleted: Boolean,
    dayNote: DayNoteEntity,
    weeklyGymCount: Int,
    weeklyRunCount: Int,
    onToggle: () -> Unit,
    onDeleteCustom: () -> Unit,
    onSaveGratitude: (String) -> Unit,
    onSaveWins: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val isGratitude = challenge.id == "default_9"
    val isWins = challenge.id == "default_10"

    val borderColor by animateColorAsState(
        targetValue = if (isCompleted) DisciplinedGreen.copy(alpha = 0.55f) else ArcBorder,
        label = "border_color"
    )

    val cardBgColor by animateColorAsState(
        targetValue = if (isCompleted) ArcCardSelected else ArcCard,
        label = "card_bg"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("challenge_card_${challenge.id}"),
        shape = RoundedCornerShape(16.dp),
        color = cardBgColor,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = if (isCompleted) 2.dp else 1.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onToggle() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Number or Custom indicator
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isCompleted) DisciplinedGreen.copy(alpha = 0.15f) else ArcNavyDark)
                        .border(
                            1.dp,
                            if (isCompleted) DisciplinedGreen.copy(alpha = 0.4f) else ArcBorder,
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (challenge.isCustom) "+" else String.format("%02d", challenge.orderIndex),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = if (isCompleted) DisciplinedGreen else IceCyan
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isCompleted) DisciplinedGreen.copy(alpha = 0.2f) else IceCyan.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = IconHelper.getIconForType(challenge.iconType),
                        contentDescription = challenge.title,
                        tint = if (isCompleted) DisciplinedGreen else IceCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title and Subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = challenge.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ArcTextPrimary
                        )

                        if (challenge.isCustom) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(IceBlue.copy(alpha = 0.2f))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "EIGENE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IceBlue
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = challenge.subtitle,
                        fontSize = 12.sp,
                        color = if (isCompleted) ArcTextSecondary else ArcTextMuted,
                        lineHeight = 16.sp
                    )

                    // Weekly Badge indicators
                    if (challenge.id == "default_4") {
                        Spacer(modifier = Modifier.height(4.dp))
                        WeeklyProgressTag(
                            current = weeklyGymCount,
                            target = 4,
                            unit = "Gym Einheiten diese Woche"
                        )
                    } else if (challenge.id == "default_5") {
                        Spacer(modifier = Modifier.height(4.dp))
                        WeeklyProgressTag(
                            current = weeklyRunCount,
                            target = 1,
                            unit = "Lauf diese Woche"
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Delete custom challenge button
                if (challenge.isCustom) {
                    IconButton(
                        onClick = onDeleteCustom,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("delete_challenge_${challenge.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Löschen",
                            tint = ArcTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Checkbox button
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("checkbox_task_${challenge.id}")
                        .clickable { onToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    val scale by animateFloatAsState(
                        targetValue = if (isCompleted) 1.05f else 1f,
                        animationSpec = spring(),
                        label = "check_scale"
                    )

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(
                                if (isCompleted) DisciplinedGreen else Color.Transparent
                            )
                            .border(
                                width = 2.dp,
                                color = if (isCompleted) DisciplinedGreen else ArcBorder,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Erledigt",
                                tint = ArcNavyDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Expander for Gratitude and 5 Wins
            if (isGratitude || isWins) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isGratitude) {
                            if (dayNote.gratitudeNote.isNotBlank()) "✍️ Dankbarkeit: \"${dayNote.gratitudeNote.take(28)}...\"" else "✍️ Gedanken eintragen..."
                        } else {
                            val winCount = listOf(dayNote.win1, dayNote.win2, dayNote.win3, dayNote.win4, dayNote.win5).count { it.isNotBlank() }
                            "🏆 $winCount / 5 Erfolge eingetragen (Tippen zum Bearbeiten)"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = IceBlue
                    )

                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Aufklappen",
                        tint = IceBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    if (isGratitude) {
                        GratitudeInputBlock(
                            initialText = dayNote.gratitudeNote,
                            onSave = onSaveGratitude
                        )
                    } else if (isWins) {
                        WinsInputBlock(
                            win1 = dayNote.win1,
                            win2 = dayNote.win2,
                            win3 = dayNote.win3,
                            win4 = dayNote.win4,
                            win5 = dayNote.win5,
                            onSave = onSaveWins
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyProgressTag(
    current: Int,
    target: Int,
    unit: String
) {
    val reached = current >= target
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (reached) DisciplinedGreen.copy(alpha = 0.15f) else IceCyan.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = "$current / $target $unit",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (reached) DisciplinedGreen else IceCyan
        )
    }
}

@Composable
private fun GratitudeInputBlock(
    initialText: String,
    onSave: (String) -> Unit
) {
    var text by remember(initialText) { mutableStateOf(initialText) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onSave(it)
            },
            placeholder = {
                Text(
                    "Wofür bist du heute dankbar? (z.B. Gesundheit, Energie, Familie...)",
                    fontSize = 12.sp,
                    color = ArcTextMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("gratitude_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = IceCyan,
                unfocusedBorderColor = ArcBorder,
                focusedTextColor = ArcTextPrimary,
                unfocusedTextColor = ArcTextPrimary,
                focusedContainerColor = ArcNavyDark,
                unfocusedContainerColor = ArcNavyDark
            ),
            shape = RoundedCornerShape(10.dp),
            maxLines = 3
        )
    }
}

@Composable
private fun WinsInputBlock(
    win1: String,
    win2: String,
    win3: String,
    win4: String,
    win5: String,
    onSave: (String, String, String, String, String) -> Unit
) {
    var w1 by remember(win1) { mutableStateOf(win1) }
    var w2 by remember(win2) { mutableStateOf(win2) }
    var w3 by remember(win3) { mutableStateOf(win3) }
    var w4 by remember(win4) { mutableStateOf(win4) }
    var w5 by remember(win5) { mutableStateOf(win5) }

    fun emitChange(new1: String, new2: String, new3: String, new4: String, new5: String) {
        onSave(new1, new2, new3, new4, new5)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Deine 5 täglichen Erfolge:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = GoldArc
        )

        Spacer(modifier = Modifier.height(6.dp))

        listOf(
            Triple(1, w1) { v: String -> w1 = v; emitChange(v, w2, w3, w4, w5) },
            Triple(2, w2) { v: String -> w2 = v; emitChange(w1, v, w3, w4, w5) },
            Triple(3, w3) { v: String -> w3 = v; emitChange(w1, w2, v, w4, w5) },
            Triple(4, w4) { v: String -> w4 = v; emitChange(w1, w2, w3, v, w5) },
            Triple(5, w5) { v: String -> w5 = v; emitChange(w1, w2, w3, w4, v) }
        ).forEach { (idx, value, update) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#$idx",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (value.isNotBlank()) GoldArc else ArcTextMuted,
                    modifier = Modifier.width(24.dp)
                )

                OutlinedTextField(
                    value = value,
                    onValueChange = update,
                    placeholder = {
                        Text(
                            when (idx) {
                                1 -> "1. Disziplin am Morgen durchgezogen"
                                2 -> "2. Workout ohne Ausreden absolviert"
                                3 -> "3. 10.000 Schritte geschafft"
                                4 -> "4. Clean gegessen"
                                else -> "5. Tag fokussiert beendet"
                            },
                            fontSize = 11.sp,
                            color = ArcTextMuted
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("win_input_$idx"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldArc,
                        unfocusedBorderColor = ArcBorder,
                        focusedTextColor = ArcTextPrimary,
                        unfocusedTextColor = ArcTextPrimary,
                        focusedContainerColor = ArcNavyDark,
                        unfocusedContainerColor = ArcNavyDark
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }
        }
    }
}
