package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.FriendEntity
import com.example.ui.dialogs.AddFriendDialog
import com.example.ui.dialogs.FriendDetailDialog
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcCardSelected
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcProgressBarBackground
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.GoldArc
import com.example.ui.theme.IceBlue
import com.example.ui.theme.IceCyan

data class LeaderboardItem(
    val id: String,
    val name: String,
    val avatar: String,
    val percentage: Int,
    val completedCount: Int,
    val totalCount: Int,
    val streak: Int,
    val status: String,
    val isMe: Boolean,
    val originalFriend: FriendEntity?
)

@Composable
fun FriendsView(
    friends: List<FriendEntity>,
    myPercentage: Int,
    myCompletedCount: Int,
    allChallenges: List<ChallengeEntity>,
    myChecks: Map<String, Boolean>,
    myStreak: Int,
    onAddFriend: (String, String, String) -> Unit,
    onSendNudge: (String, String) -> Unit,
    onDeleteFriend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedFriendForDetail by remember { mutableStateOf<FriendEntity?>(null) }

    // Combine current user with friends into ranked leaderboard
    val leaderboard = remember(friends, myPercentage, myCompletedCount, myStreak, allChallenges.size) {
        val total = allChallenges.size.coerceAtLeast(1)
        val list = mutableListOf<LeaderboardItem>()

        list.add(
            LeaderboardItem(
                id = "me",
                name = "Du (Ich)",
                avatar = "❄️",
                percentage = myPercentage,
                completedCount = myCompletedCount,
                totalCount = total,
                streak = myStreak,
                status = "Dein heutiger Winter Arc Fortschritt",
                isMe = true,
                originalFriend = null
            )
        )

        friends.forEach { f ->
            list.add(
                LeaderboardItem(
                    id = f.id,
                    name = f.name,
                    avatar = f.avatarEmoji,
                    percentage = f.todayPercentage,
                    completedCount = f.completedCount,
                    totalCount = f.totalCount,
                    streak = f.streak,
                    status = f.statusMessage,
                    isMe = false,
                    originalFriend = f
                )
            )
        }

        list.sortedWith(
            compareByDescending<LeaderboardItem> { it.percentage }
                .thenByDescending { it.streak }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("friends_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "WINTER ARC SQUAD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = IceCyan,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Freunde & Rangliste",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = ArcTextPrimary
                    )
                }

                Button(
                    onClick = { showAddDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IceCyan,
                        contentColor = ArcNavyDark
                    ),
                    modifier = Modifier.testTag("btn_add_friend_open")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Freund", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Vergleiche deine heutige Disziplin live mit deinen Trainingspartnern und spornt euch gegenseitig an.",
                fontSize = 12.sp,
                color = ArcTextSecondary,
                lineHeight = 16.sp
            )
        }

        // Leaderboard List
        itemsIndexed(leaderboard) { index, item ->
            val rank = index + 1
            val rankBadge = when (rank) {
                1 -> "🥇"
                2 -> "🥈"
                3 -> "🥉"
                else -> "#$rank"
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (item.isMe) ArcCardSelected else ArcCard,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (item.isMe) IceCyan.copy(alpha = 0.6f) else ArcBorder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!item.isMe && item.originalFriend != null) {
                            selectedFriendForDetail = item.originalFriend
                        }
                    }
                    .testTag("leaderboard_item_${item.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rank badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ArcNavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rankBadge,
                            fontSize = if (rank <= 3) 18.sp else 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (rank == 1) GoldArc else ArcTextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Avatar
                    Text(
                        text = item.avatar,
                        fontSize = 26.sp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Name & Status
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (item.isMe) IceCyan else ArcTextPrimary
                            )
                            if (item.isMe) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(IceCyan.copy(alpha = 0.2f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "DU",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = IceCyan
                                    )
                                }
                            }
                        }

                        Text(
                            text = item.status,
                            fontSize = 11.sp,
                            color = ArcTextMuted,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(CircleShape)
                                .background(ArcProgressBarBackground)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = (item.percentage / 100f).coerceIn(0f, 1f))
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (item.percentage >= 80) DisciplinedGreen else IceCyan
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Percentage and Streak
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${item.percentage}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = if (item.percentage >= 80) DisciplinedGreen else ArcTextPrimary
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = GoldArc,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${item.streak}d",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldArc
                            )
                        }
                    }

                    if (!item.isMe) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Details",
                            tint = ArcTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showAddDialog) {
        AddFriendDialog(
            onDismiss = { showAddDialog = false },
            onAddFriend = onAddFriend
        )
    }

    selectedFriendForDetail?.let { friend ->
        FriendDetailDialog(
            friend = friend,
            myPercentage = myPercentage,
            myCompletedCount = myCompletedCount,
            allChallenges = allChallenges,
            myChecks = myChecks,
            onNudge = { text ->
                onSendNudge(friend.id, text)
            },
            onDeleteFriend = {
                onDeleteFriend(friend.id)
                selectedFriendForDetail = null
            },
            onDismiss = { selectedFriendForDetail = null }
        )
    }
}
