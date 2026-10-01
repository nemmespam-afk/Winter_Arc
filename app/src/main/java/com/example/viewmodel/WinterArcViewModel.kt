package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.WinterArcRepository
import com.example.data.entities.ChallengeCheckEntity
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.DayNoteEntity
import com.example.data.entities.FriendEntity
import com.example.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DayHistoryItem(
    val date: String,
    val dayLabel: String,
    val percentage: Int,
    val completedCount: Int,
    val totalCount: Int
)

data class OverallStats(
    val totalLoggedDays: Int,
    val overallPercentage: Int,
    val streak: Int,
    val perfectDays: Int,
    val totalTasksCompleted: Int
)

class WinterArcViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WinterArcRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = WinterArcRepository(db.winterArcDao())
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    val selectedDate = MutableStateFlow(DateUtils.todayDateString())

    val challenges: StateFlow<List<ChallengeEntity>> = repository.allChallenges
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentChecks: StateFlow<Map<String, Boolean>> = selectedDate
        .flatMapLatest { date ->
            repository.getChecksForDate(date)
        }
        .combine(challenges) { checks, allChallenges ->
            val checkMap = checks.associate { it.challengeId to it.isCompleted }
            allChallenges.associate { it.id to (checkMap[it.id] ?: false) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val currentDayNote: StateFlow<DayNoteEntity> = selectedDate
        .flatMapLatest { date ->
            repository.getDayNote(date)
        }
        .combine(selectedDate) { note, date ->
            note ?: DayNoteEntity(date = date)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DayNoteEntity(date = DateUtils.todayDateString()))

    val allChecks: StateFlow<List<ChallengeCheckEntity>> = repository.allChecks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val friends: StateFlow<List<FriendEntity>> = repository.allFriends
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayCompletedCount: StateFlow<Int> = currentChecks
        .combine(challenges) { checks, allChallenges ->
            allChallenges.count { checks[it.id] == true }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val todayTotalCount: StateFlow<Int> = challenges
        .combine(challenges) { list, _ -> list.size.coerceAtLeast(1) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 10)

    val todayPercentage: StateFlow<Int> = combine(todayCompletedCount, challenges) { completed, allChallenges ->
        val total = allChallenges.size
        if (total > 0) (completed * 100) / total else 0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val weeklyGymCount: StateFlow<Int> = combine(selectedDate, allChecks) { date, checks ->
        val weekDays = DateUtils.getWeekDays(date).toSet()
        checks.filter { it.date in weekDays && it.challengeId == "default_4" && it.isCompleted }.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val weeklyRunCount: StateFlow<Int> = combine(selectedDate, allChecks) { date, checks ->
        val weekDays = DateUtils.getWeekDays(date).toSet()
        checks.filter { it.date in weekDays && it.challengeId == "default_5" && it.isCompleted }.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val streakCount: StateFlow<Int> = combine(allChecks, challenges) { checks, allChallenges ->
        if (allChallenges.isEmpty()) return@combine 0
        val total = allChallenges.size
        val dateGroup = checks.groupBy { it.date }
        var streak = 0
        var checkDate = DateUtils.todayDateString()

        val todayChecks = dateGroup[checkDate].orEmpty()
        val todayCompleted = todayChecks.count { it.isCompleted }
        val todayPct = if (total > 0) (todayCompleted * 100) / total else 0

        if (todayPct >= 70) {
            streak++
            checkDate = DateUtils.getOffsetDay(checkDate, -1)
        } else {
            val yesterday = DateUtils.getOffsetDay(checkDate, -1)
            val yesterdayChecks = dateGroup[yesterday].orEmpty()
            val yPct = if (total > 0) (yesterdayChecks.count { it.isCompleted } * 100) / total else 0
            if (yPct >= 70) {
                checkDate = yesterday
            } else {
                return@combine 0
            }
        }

        while (true) {
            val dayChecks = dateGroup[checkDate].orEmpty()
            val completed = dayChecks.count { it.isCompleted }
            val pct = if (total > 0) (completed * 100) / total else 0
            if (pct >= 70) {
                streak++
                checkDate = DateUtils.getOffsetDay(checkDate, -1)
            } else {
                break
            }
        }
        streak
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val last7DaysHistory: StateFlow<List<DayHistoryItem>> = combine(allChecks, challenges, selectedDate) { checks, allChallenges, _ ->
        val total = allChallenges.size.coerceAtLeast(1)
        val checksByDate = checks.groupBy { it.date }
        val last7 = DateUtils.getLast7Days()

        last7.map { date ->
            val dayChecks = checksByDate[date].orEmpty()
            val completed = dayChecks.count { it.isCompleted }
            val pct = (completed * 100) / total
            DayHistoryItem(
                date = date,
                dayLabel = DateUtils.getDayOfWeekShort(date),
                percentage = pct,
                completedCount = completed,
                totalCount = total
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val overallStats: StateFlow<OverallStats> = combine(allChecks, challenges, streakCount) { checks, allChallenges, streak ->
        val totalChallenges = allChallenges.size.coerceAtLeast(1)
        val byDate = checks.groupBy { it.date }
        val totalLoggedDays = byDate.keys.size
        val totalTasksCompleted = checks.count { it.isCompleted }
        val perfectDays = byDate.count { (_, dayChecks) ->
            val completed = dayChecks.count { it.isCompleted }
            completed >= totalChallenges
        }
        val possible = totalLoggedDays * totalChallenges
        val overallPct = if (possible > 0) (totalTasksCompleted * 100) / possible else 0

        OverallStats(
            totalLoggedDays = totalLoggedDays,
            overallPercentage = overallPct,
            streak = streak,
            perfectDays = perfectDays,
            totalTasksCompleted = totalTasksCompleted
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OverallStats(0, 0, 0, 0, 0))

    fun selectDate(date: String) {
        selectedDate.value = date
    }

    fun previousDay() {
        selectedDate.value = DateUtils.getOffsetDay(selectedDate.value, -1)
    }

    fun nextDay() {
        selectedDate.value = DateUtils.getOffsetDay(selectedDate.value, 1)
    }

    fun goToToday() {
        selectedDate.value = DateUtils.todayDateString()
    }

    fun toggleChallenge(challengeId: String) {
        viewModelScope.launch {
            repository.toggleChallenge(selectedDate.value, challengeId)
        }
    }

    fun addCustomChallenge(
        title: String,
        subtitle: String,
        cadence: String,
        cadenceLabel: String,
        iconType: String
    ) {
        viewModelScope.launch {
            repository.addCustomChallenge(title, subtitle, cadence, cadenceLabel, iconType)
        }
    }

    fun deleteChallenge(challengeId: String) {
        viewModelScope.launch {
            repository.deleteChallenge(challengeId)
        }
    }

    fun saveGratitude(note: String) {
        viewModelScope.launch {
            repository.saveGratitude(selectedDate.value, note)
            if (note.isNotBlank()) {
                // Also ensure gratitude challenge is marked completed
                val current = currentChecks.value["default_9"] ?: false
                if (!current) {
                    repository.toggleChallenge(selectedDate.value, "default_9")
                }
            }
        }
    }

    fun saveWins(w1: String, w2: String, w3: String, w4: String, w5: String) {
        viewModelScope.launch {
            repository.saveWins(selectedDate.value, w1, w2, w3, w4, w5)
            val filled = listOf(w1, w2, w3, w4, w5).count { it.isNotBlank() }
            if (filled >= 5) {
                val current = currentChecks.value["default_10"] ?: false
                if (!current) {
                    repository.toggleChallenge(selectedDate.value, "default_10")
                }
            }
        }
    }

    fun setAllComplete(complete: Boolean) {
        viewModelScope.launch {
            repository.setAllChallengesComplete(selectedDate.value, complete)
        }
    }

    fun addFriend(name: String, avatar: String, status: String) {
        viewModelScope.launch {
            repository.addFriend(name, avatar, status)
        }
    }

    fun sendFriendNudge(friendId: String, nudgeText: String) {
        viewModelScope.launch {
            repository.sendNudge(friendId, nudgeText)
        }
    }

    fun deleteFriend(friendId: String) {
        viewModelScope.launch {
            repository.deleteFriend(friendId)
        }
    }
}
