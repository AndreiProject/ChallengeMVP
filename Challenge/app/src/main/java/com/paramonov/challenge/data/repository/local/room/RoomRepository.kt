package com.paramonov.challenge.data.repository.local.room

import com.paramonov.challenge.data.repository.model.Category

interface RoomRepository {
    fun getCategoriesWithChallenges(userId: String): List<Category>
    fun insertCategoryWithChallenges(category: Category, userId: String)
}