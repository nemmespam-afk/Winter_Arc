package com.example.ui.dialogs

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.components.IconHelper
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.IceCyan

@Composable
fun AddChallengeDialog(
    onDismiss: () -> Unit,
    onAddChallenge: (title: String, subtitle: String, cadence: String, cadenceLabel: String, iconType: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var isDaily by remember { mutableStateOf(true) }
    var selectedIcon by remember { mutableStateOf("book") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ArcCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("add_challenge_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "NEUE DISZIPLIN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = IceCyan,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Challenge hinzufügen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = ArcTextPrimary
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Schließen",
                            tint = ArcTextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title Input
                Text(
                    text = "Name der Disziplin",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        errorMessage = null
                    },
                    placeholder = {
                        Text("z.B. 30 MINUTEN LESEN", color = ArcTextMuted, fontSize = 13.sp)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_challenge_title"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IceCyan,
                        unfocusedBorderColor = ArcBorder,
                        focusedTextColor = ArcTextPrimary,
                        unfocusedTextColor = ArcTextPrimary,
                        focusedContainerColor = ArcNavyDark,
                        unfocusedContainerColor = ArcNavyDark
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Subtitle Input
                Text(
                    text = "Beschreibung & Regel",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = subtitle,
                    onValueChange = { subtitle = it },
                    placeholder = {
                        Text("z.B. Kein Smartphone vor dem Schlafen, stattdessen Buch.", color = ArcTextMuted, fontSize = 13.sp)
                    },
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_challenge_subtitle"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IceCyan,
                        unfocusedBorderColor = ArcBorder,
                        focusedTextColor = ArcTextPrimary,
                        unfocusedTextColor = ArcTextPrimary,
                        focusedContainerColor = ArcNavyDark,
                        unfocusedContainerColor = ArcNavyDark
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Cadence Filter Chips
                Text(
                    text = "Häufigkeit",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = isDaily,
                        onClick = { isDaily = true },
                        label = { Text("Täglich") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = IceCyan,
                            selectedLabelColor = ArcNavyDark,
                            containerColor = ArcNavyDark,
                            labelColor = ArcTextSecondary
                        )
                    )
                    FilterChip(
                        selected = !isDaily,
                        onClick = { isDaily = false },
                        label = { Text("Wöchentlich") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = IceCyan,
                            selectedLabelColor = ArcNavyDark,
                            containerColor = ArcNavyDark,
                            labelColor = ArcTextSecondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Icon Selection
                Text(
                    text = "Symbol wählen",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(100.dp)
                ) {
                    items(IconHelper.availableCustomIcons) { (iconKey, label) ->
                        val isSelected = selectedIcon == iconKey
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) IceCyan.copy(alpha = 0.2f) else ArcNavyDark)
                                .border(
                                    1.dp,
                                    if (isSelected) IceCyan else ArcBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedIcon = iconKey },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = IconHelper.getIconForType(iconKey),
                                contentDescription = label,
                                tint = if (isSelected) IceCyan else ArcTextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage!!,
                        color = androidx.compose.ui.graphics.Color(0xFFEF4444),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ArcTextSecondary)
                    ) {
                        Text("Abbrechen")
                    }

                    Button(
                        onClick = {
                            if (title.isBlank()) {
                                errorMessage = "Bitte gib einen Namen für die Challenge ein."
                            } else {
                                val cadence = if (isDaily) "DAILY" else "WEEKLY"
                                val cadenceLabel = if (isDaily) "Täglich" else "Wöchentlich"
                                val finalSubtitle = if (subtitle.isBlank()) "Individuelle Winter Arc Disziplin" else subtitle.trim()
                                onAddChallenge(title, finalSubtitle, cadence, cadenceLabel, selectedIcon)
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_submit_add_challenge"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IceCyan,
                            contentColor = ArcNavyDark
                        )
                    ) {
                        Text("Hinzufügen", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
