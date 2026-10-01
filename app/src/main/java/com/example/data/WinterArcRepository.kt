package com.example.data

import com.example.data.daos.WinterArcDao
import com.example.data.entities.ChallengeCheckEntity
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.DayNoteEntity
import com.example.data.entities.FriendEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class WinterArcRepository(private val dao: WinterArcDao) {

    val allChallenges: Flow<List<ChallengeEntity>> = dao.getAllActiveChallenges()
    val allChecks: Flow<List<ChallengeCheckEntity>> = dao.getAllChecks()
    val allFriends: Flow<List<FriendEntity>> = dao.getAllFriends()

    suspend fun ensureInitialized() {
        val existing = dao.getChallengesOnce()
        if (existing.isEmpty()) {
            dao.insertInitialChallenges(WinterArcDefaults.defaultChallenges)
        }
        val existingFriends = dao.getFriendsOnce()
        if (existingFriends.isEmpty()) {
            dao.insertInitialFriends(WinterArcDefaults.defaultFriends)
        }
    }

    fun getChecksForDate(date: String): Flow<List<ChallengeCheckEntity>> {
        return dao.getChecksForDate(date)
    }

    fun getDayNote(date: String): Flow<DayNoteEntity?> {
        return dao.getDayNote(date)
    }

    suspend fun toggleChallenge(date: String, challengeId: String) {
        val current = dao.getCheckOnce(date, challengeId)
        val isCompleted = current?.isCompleted ?: false
        val newCheck = ChallengeCheckEntity(
            date = date,
            challengeId = challengeId,
            isCompleted = !isCompleted,
            updatedAt = System.currentTimeMillis()
        )
        dao.insertOrUpdateCheck(newCheck)
    }

    suspend fun setAllChallengesComplete(date: String, complete: Boolean) {
        val challenges = dao.getChallengesOnce()
        challenges.forEach { ch ->
            dao.insertOrUpdateCheck(
                ChallengeCheckEntity(
                    date = date,
                    challengeId = ch.id,
                    isCompleted = complete,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun addCustomChallenge(
        title: String,
        subtitle: String,
        cadence: String,
        cadenceLabel: String,
        iconType: String
    ) {
        val current = dao.getChallengesOnce()
        val nextOrder = (current.maxOfOrNull { it.orderIndex } ?: 10) + 1
        val newChallenge = ChallengeEntity(
            id = "custom_" + UUID.randomUUID().toString().take(8),
            title = title.trim().uppercase(),
            subtitle = subtitle.trim(),
            cadence = cadence,
            cadenceLabel = cadenceLabel,
            targetCount = 1,
            iconType = iconType,
            isCustom = true,
            isActive = true,
            orderIndex = nextOrder,
            disciplineTip = "Deine persönliche Disziplin für den Winter Arc."
        )
        dao.insertChallenge(newChallenge)
    }

    suspend fun deleteChallenge(challengeId: String) {
        dao.deleteChallenge(challengeId)
        dao.deleteChecksForChallenge(challengeId)
    }

    suspend fun saveGratitude(date: String, note: String) {
        val current = dao.getDayNoteOnce(date) ?: DayNoteEntity(date = date)
        dao.insertOrUpdateNote(
            current.copy(
                gratitudeNote = note,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun saveWins(
        date: String,
        w1: String,
        w2: String,
        w3: String,
        w4: String,
        w5: String
    ) {
        val current = dao.getDayNoteOnce(date) ?: DayNoteEntity(date = date)
        dao.insertOrUpdateNote(
            current.copy(
                win1 = w1,
                win2 = w2,
                win3 = w3,
                win4 = w4,
                win5 = w5,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun addFriend(name: String, avatar: String, status: String) {
        val id = "friend_" + UUID.randomUUID().toString().take(8)
        val friend = FriendEntity(
            id = id,
            name = name.trim(),
            avatarEmoji = if (avatar.isBlank()) "🥋" else avatar,
            streak = (1..5).random(),
            todayPercentage = (5..9).random() * 10,
            completedCount = (5..9).random(),
            totalCount = 10,
            weeklyAverage = (70..95).random(),
            statusMessage = if (status.isBlank()) "Im Winter Arc Fokus!" else status.trim(),
            isCustomAdded = true,
            completedChallengeIds = "default_1,default_2,default_3,default_6,default_7"
        )
        dao.insertFriend(friend)
    }

    suspend fun sendNudge(friendId: String, nudgeText: String) {
        dao.updateFriendNudge(friendId, nudgeText)
    }

    suspend fun deleteFriend(friendId: String) {
        dao.deleteFriend(friendId)
    }
}
