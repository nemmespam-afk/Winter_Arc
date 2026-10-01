package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.daos.WinterArcDao
import com.example.data.entities.ChallengeCheckEntity
import com.example.data.entities.ChallengeEntity
import com.example.data.entities.DayNoteEntity
import com.example.data.entities.FriendEntity

@Database(
    entities = [
        ChallengeEntity::class,
        ChallengeCheckEntity::class,
        DayNoteEntity::class,
        FriendEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun winterArcDao(): WinterArcDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "winter_arc_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
