package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.ChallengeCatalog
import com.example.ui.components.ChallengeItemCard
import com.example.ui.components.CircularProgressGraphic
import com.example.ui.components.DateSelectorStrip
import com.example.ui.components.RulesGuideView
import com.example.ui.components.StatisticsView
import com.example.ui.theme.ArcBorder
import com.example.ui.theme.ArcCard
import com.example.ui.theme.ArcNavyDark
import com.example.ui.theme.ArcSurface
import com.example.ui.theme.ArcTextMuted
import com.example.ui.theme.ArcTextPrimary
import com.example.ui.theme.ArcTextSecondary
import com.example.ui.theme.DisciplinedGreen
import com.example.ui.theme.GoldArc
import com.example.ui.theme.IceCyan
import com.example.viewmodel.WinterArcViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: WinterArcViewModel,
    modifier: Modifier = Modifier
) {
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val record by viewModel.currentRecord.collectAsStateWithLifecycle()
    val allRecords by viewModel.allRecords.collectAsStateWithLifecycle()
    val weeklyGymCount by viewModel.weeklyGymCount.collectAsStateWithLifecycle()
    val weeklyRunCount by viewModel.weeklyRunCount.collectAsStateWithLifecycle()
    val streakCount by viewModel.streakCount.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("winter_arc_main_screen"),
        containerColor = ArcNavyDark,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AcUnit,
                            contentDescription = "Winter Arc Logo",
                            tint = IceCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "WINTER ARC",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            letterSpacing = 1.5.sp,
                            color = ArcTextPrimary
                        )
                    }
                },
                actions = {
                    // Streak Pill
                    if (streakCount > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(GoldArc.copy(alpha = 0.15f))
                                .border(1.dp, GoldArc.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Streak",
                                tint = GoldArc,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$streakCount",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldArc
                            )
                        }
                    }

                    // More Menu
                    Box {
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("top_menu_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menü",
                                tint = ArcTextSecondary
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(ArcCard)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Alle 10 abhaken", color = IceCyan) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = IceCyan
                                    )
                                },
                                onClick = {
                                    viewModel.setAllComplete(true)
                                    showMenu = false
                                },
                                modifier = Modifier.testTag("action_complete_all")
                            )

                            DropdownMenuItem(
                                text = { Text("Tag zurücksetzen", color = ArcTextSecondary) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.RestartAlt,
                                        contentDescription = null,
                                        tint = ArcTextSecondary
                                    )
                                },
                                onClick = {
                                    viewModel.setAllComplete(false)
                                    showMenu = false
                                },
                                modifier = Modifier.testTag("action_reset_day")
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ArcNavyDark
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = ArcSurface,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FormatListBulleted,
                            contentDescription = "Checkliste"
                        )
                    },
                    label = { Text("Checkliste") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ArcNavyDark,
                        selectedTextColor = IceCyan,
                        indicatorColor = IceCyan,
                        unselectedIconColor = ArcTextMuted,
                        unselectedTextColor = ArcTextMuted
                    ),
                    modifier = Modifier.testTag("tab_checklist")
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.QueryStats,
                            contentDescription = "Graphik & Quote"
                        )
                    },
                    label = { Text("Graphik & Quote") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ArcNavyDark,
                        selectedTextColor = IceCyan,
                        indicatorColor = IceCyan,
                        unselectedIconColor = ArcTextMuted,
                        unselectedTextColor = ArcTextMuted
                    ),
                    modifier = Modifier.testTag("tab_stats")
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Regeln"
                        )
                    },
                    label = { Text("Regeln") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ArcNavyDark,
                        selectedTextColor = IceCyan,
                        indicatorColor = IceCyan,
                        unselectedIconColor = ArcTextMuted,
                        unselectedTextColor = ArcTextMuted
                    ),
                    modifier = Modifier.testTag("tab_rules")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> ChecklistTab(
                    selectedDate = selectedDate,
                    record = record,
                    records = allRecords,
                    weeklyGymCount = weeklyGymCount,
                    weeklyRunCount = weeklyRunCount,
                    onToggleTask = { viewModel.toggleTask(it) },
                    onSelectDate = { viewModel.selectDate(it) },
                    onPreviousDay = { viewModel.previousDay() },
                    onNextDay = { viewModel.nextDay() },
                    onToday = { viewModel.goToToday() },
                    onSaveGratitude = { note, done -> viewModel.saveGratitude(note, done) },
                    onSaveWins = { w1, w2, w3, w4, w5, done -> viewModel.saveWins(w1, w2, w3, w4, w5, done) }
                )

                1 -> StatisticsView(
                    records = allRecords,
                    currentStreak = streakCount,
                    selectedDate = selectedDate,
                    onSelectDate = {
                        viewModel.selectDate(it)
                        selectedTab = 0
                    }
                )

                2 -> RulesGuideView()
            }
        }
    }
}

@Composable
private fun ChecklistTab(
    selectedDate: String,
    record: com.example.data.DayRecord,
    records: List<com.example.data.DayRecord>,
    weeklyGymCount: Int,
    weeklyRunCount: Int,
    onToggleTask: (Int) -> Unit,
    onSelectDate: (String) -> Unit,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit,
    onSaveGratitude: (String, Boolean) -> Unit,
    onSaveWins: (String, String, String, String, String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Date Navigation Strip
        item {
            Spacer(modifier = Modifier.height(4.dp))
            DateSelectorStrip(
                selectedDate = selectedDate,
                records = records,
                onSelectDate = onSelectDate,
                onPreviousDay = onPreviousDay,
                onNextDay = onNextDay,
                onToday = onToday
            )
        }

        // Circular Percentage Graphic
        item {
            CircularProgressGraphic(
                percentage = record.percentage,
                completedCount = record.completedCount,
                totalCount = 10,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 100% Celebration Banner
        if (record.percentage == 100) {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DisciplinedGreen.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DisciplinedGreen.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🏆",
                            fontSize = 26.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "100% Winter Arc Perfektion!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = DisciplinedGreen
                            )
                            Text(
                                text = "Alle 10 Disziplinen gemeistert. Starker Tag!",
                                fontSize = 12.sp,
                                color = ArcTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Header for Challenges
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DIE 10 DISZIPLINEN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = IceCyan,
                    letterSpacing = 1.2.sp
                )

                Text(
                    text = "${record.completedCount} / 10 erledigt",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = ArcTextSecondary
                )
            }
        }

        // The 10 Challenges
        items(ChallengeCatalog.challenges) { ch ->
            ChallengeItemCard(
                challenge = ch,
                record = record,
                weeklyGymCount = weeklyGymCount,
                weeklyRunCount = weeklyRunCount,
                onToggle = { onToggleTask(ch.id) },
                onSaveGratitude = onSaveGratitude,
                onSaveWins = onSaveWins
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
