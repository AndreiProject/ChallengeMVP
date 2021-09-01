package com.paramonov.challenge.data.source.room

import android.content.Context
import androidx.room.*
import com.paramonov.challenge.data.repository.local.room.model.CategoryChallenges
import com.paramonov.challenge.data.repository.local.room.dao.*
import com.paramonov.challenge.data.repository.model.*

@Database(
    entities = [Category::class, Challenge::class, CategoryChallenges::class],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getChallengeDao(): ChallengeDao
    abstract fun getCategoryChallengesDao(): CategoryChallengesDao

    companion object {

        @Volatile
        private var database: AppRoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppRoomDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "database"
                ).build()
            }
            return database!!
        }
    }
}