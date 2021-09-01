package com.paramonov.challenge.data.repository.local.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.paramonov.challenge.data.repository.model.Challenge

@Dao
abstract class ChallengeDao {

    @Query(
        "select * from challenge " +
                "join category_challenges on category_challenges.challenge_id = challenge.id " +
                "where category_challenges.category_id = :categoryId " +
                "and category_challenges.user_id = :userId"
    )
    abstract fun getCategoryChallenges(categoryId: String, userId: String): List<Challenge>

    @Insert(onConflict = REPLACE)
    abstract fun insert(challenge: Challenge)

    @Delete
    abstract fun delete(challenge: Challenge)
}