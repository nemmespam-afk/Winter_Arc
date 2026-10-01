package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DayRecordDao {
    @Query("SELECT * FROM day_records WHERE date = :date")
    fun getRecord(date: String): Flow<DayRecord?>

    @Query("SELECT * FROM day_records WHERE date = :date")
    suspend fun getRecordOnce(date: String): DayRecord?

    @Query("SELECT * FROM day_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<DayRecord>>

    @Query("SELECT * FROM day_records WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getRecordsBetween(startDate: String, endDate: String): Flow<List<DayRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: DayRecord)

    @Query("DELETE FROM day_records WHERE date = :date")
    suspend fun deleteRecord(date: String)
}
