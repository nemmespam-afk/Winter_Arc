package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.FriendEntity
import com.example.ui.components.IconHelper
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
fun FriendDetailDialog(
    friend: FriendEntity,
    myPercentage: Int,
    myCompletedCount: Int,
    allChallenges: List<ChallengeEntity>,
    myChecks: Map<String, Boolean>,
    onNudge: (String) -> Unit,
    onDeleteFriend: () -> Unit,
    onDismiss: () -> Unit
) {
    var nudgeFeedback by remember { mutableStateOf(friend.lastNudge) }

    val completedIds = remember(friend.completedChallengeIds) {
        friend.completedChallengeIds.split(",").map { it.trim() }.toSet()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ArcCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("friend_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = friend.avatarEmoji,
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = friend.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = ArcTextPrimary
                            )
                            Text(
                                text = friend.statusMessage,
                                fontSize = 12.sp,
                                color = ArcTextSecondary
                            )
                        }
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

                // Direct Comparison Bar (Du vs. Freund)
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ArcNavyDark,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ArcBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Column
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "DU",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = IceCyan,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$myPercentage%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = if (myPercentage >= 80) DisciplinedGreen else ArcTextPrimary
                            )
                            Text(
                                text = "$myCompletedCount/${allChallenges.size} Punkte",
                                fontSize = 11.sp,
                                color = ArcTextSecondary
                            )
                        }

                        // VS Divider
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(ArcCardSelected),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "VS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = ArcTextMuted
                            )
                        }

                        // Friend Column
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = friend.name.uppercase().take(8),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldArc,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${friend.todayPercentage}%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = if (friend.todayPercentage >= 80) DisciplinedGreen else ArcTextPrimary
                            )
                            Text(
                                text = "${friend.completedCount}/${friend.totalCount} Punkte",
                                fontSize = 11.sp,
                                color = ArcTextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Streak & Weekly Stat
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ArcNavyDark)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = GoldArc,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${friend.streak} Tage Streak",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldArc
                        )
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ArcNavyDark)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ø ${friend.weeklyAverage}% Disziplin",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = IceBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Was ${friend.name} heute erreicht hat:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArcTextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                // List of Challenges comparison
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(allChallenges.take(10)) { ch ->
                        val friendDone = completedIds.contains(ch.id)
                        val myDone = myChecks[ch.id] == true

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ArcNavyDark)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = IconHelper.getIconForType(ch.iconType),
                                    contentDescription = null,
                                    tint = if (friendDone) IceCyan else ArcTextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = ch.title,
                                    fontSize = 12.sp,
                                    color = if (friendDone) ArcTextPrimary else ArcTextMuted
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (friendDone) "Erledigt" else "Offen",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (friendDone) DisciplinedGreen else ArcTextMuted
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(if (friendDone) DisciplinedGreen else ArcBorder),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (friendDone) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = ArcNavyDark,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Nudge Section
                if (nudgeFeedback.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(DisciplinedGreen.copy(alpha = 0.15f))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓ $nudgeFeedback",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DisciplinedGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Motivation senden:",
                    fontSize = 11.sp,
                    color = ArcTextMuted
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val msg = "🔥 Respekt an ${friend.name} gesendet!"
                            nudgeFeedback = msg
                            onNudge(msg)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ArcCardSelected,
                            contentColor = GoldArc
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🔥 Respekt", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            val msg = "💪 Ansporn 'Zieh durch!' an ${friend.name} gesendet!"
                            nudgeFeedback = msg
                            onNudge(msg)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ArcCardSelected,
                            contentColor = IceCyan
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("💪 Ansporn", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                if (friend.isCustomAdded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            onDeleteFriend()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = androidx.compose.ui.graphics.Color(0xFFEF4444)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Freund entfernen", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
