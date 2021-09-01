package com.paramonov.challenge.data.repository.local.room.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "category_challenges", primaryKeys = ["user_id", "category_id", "challenge_id"])
data class CategoryChallenges(
    @NonNull
    @ColumnInfo(name = "user_id") var userId: String,
    @NonNull
    @ColumnInfo(name = "category_id") var categoryId: String,
    @NonNull
    @ColumnInfo(name = "challenge_id") var challengeId: String
)