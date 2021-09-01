package com.paramonov.challenge.data.repository.local.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.paramonov.challenge.data.repository.local.room.model.CategoryChallenges

@Dao
abstract class CategoryChallengesDao {

    @Insert(onConflict = REPLACE)
    abstract fun insert(categoryChallenges: CategoryChallenges)

    @Delete
    abstract fun delete(categoryChallenges: CategoryChallenges)
}