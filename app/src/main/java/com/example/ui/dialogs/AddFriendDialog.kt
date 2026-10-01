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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.IceCyan

@Composable
fun AddFriendDialog(
    onDismiss: () -> Unit,
    onAddFriend: (name: String, avatar: String, status: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("🐺") }
    var status by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val emojis = listOf("🐺", "🦁", "⚡", "🦅", "🥊", "🥋", "❄️", "🔥", "👑", "🗡️")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ArcCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("add_friend_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "COMMUNITY & SQUAD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = IceCyan,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Freund hinzufügen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = ArcTextPrimary
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Schließen",
                            tint = ArcTextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Name des Freundes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        error = null
                    },
                    placeholder = { Text("z.B. David oder @training_buddy", color = ArcTextMuted, fontSize = 13.sp) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_friend_name"),
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

                Text(
                    text = "Avatar-Symbol",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(emojis) { emoji ->
                        val isSelected = selectedEmoji == emoji
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) IceCyan.copy(alpha = 0.2f) else ArcNavyDark)
                                .border(
                                    1.dp,
                                    if (isSelected) IceCyan else ArcBorder,
                                    CircleShape
                                )
                                .clickable { selectedEmoji = emoji },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 20.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Status oder Motto (optional)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArcTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    placeholder = { Text("z.B. Frühaufsteher & Gym-Fokus", color = ArcTextMuted, fontSize = 13.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
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

                if (error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error!!, color = androidx.compose.ui.graphics.Color(0xFFEF4444), fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

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
                            if (name.isBlank()) {
                                error = "Bitte gib den Namen deines Freundes ein."
                            } else {
                                onAddFriend(name, selectedEmoji, status)
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_submit_add_friend"),
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
