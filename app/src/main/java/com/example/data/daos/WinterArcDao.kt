package com.example.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.ChallengeCheckEntity
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.DayNoteEntity
import com.example.data.entities.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WinterArcDao {

    // --- Challenges ---
    @Query("SELECT * FROM challenges WHERE isActive = 1 ORDER BY orderIndex ASC")
    fun getAllActiveChallenges(): Flow<List<ChallengeEntity>>

    @Query("SELECT * FROM challenges WHERE isActive = 1 ORDER BY orderIndex ASC")
    suspend fun getChallengesOnce(): List<ChallengeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: ChallengeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialChallenges(challenges: List<ChallengeEntity>)

    @Query("DELETE FROM challenges WHERE id = :challengeId")
    suspend fun deleteChallenge(challengeId: String)

    // --- Challenge Checks ---
    @Query("SELECT * FROM challenge_checks WHERE date = :date")
    fun getChecksForDate(date: String): Flow<List<ChallengeCheckEntity>>

    @Query("SELECT * FROM challenge_checks WHERE date = :date AND challengeId = :challengeId LIMIT 1")
    suspend fun getCheckOnce(date: String, challengeId: String): ChallengeCheckEntity?

    @Query("SELECT * FROM challenge_checks ORDER BY date DESC")
    fun getAllChecks(): Flow<List<ChallengeCheckEntity>>

    @Query("SELECT * FROM challenge_checks WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getChecksBetween(startDate: String, endDate: String): Flow<List<ChallengeCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCheck(check: ChallengeCheckEntity)

    @Query("DELETE FROM challenge_checks WHERE date = :date")
    suspend fun clearChecksForDate(date: String)

    @Query("DELETE FROM challenge_checks WHERE challengeId = :challengeId")
    suspend fun deleteChecksForChallenge(challengeId: String)

    // --- Day Notes ---
    @Query("SELECT * FROM day_notes WHERE date = :date")
    fun getDayNote(date: String): Flow<DayNoteEntity?>

    @Query("SELECT * FROM day_notes WHERE date = :date")
    suspend fun getDayNoteOnce(date: String): DayNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNote(note: DayNoteEntity)

    // --- Friends ---
    @Query("SELECT * FROM friends ORDER BY todayPercentage DESC, streak DESC")
    fun getAllFriends(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM friends")
    suspend fun getFriendsOnce(): List<FriendEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friend: FriendEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialFriends(friends: List<FriendEntity>)

    @Query("DELETE FROM friends WHERE id = :friendId")
    suspend fun deleteFriend(friendId: String)

    @Query("UPDATE friends SET lastNudge = :nudge WHERE id = :friendId")
    suspend fun updateFriendNudge(friendId: String, nudge: String)
}
