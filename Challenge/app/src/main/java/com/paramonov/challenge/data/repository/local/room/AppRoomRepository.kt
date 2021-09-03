package com.paramonov.challenge.data.repository.local.room

import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.source.room.AppRoomDatabase

class AppRoomRepository(private val roomDatabase: AppRoomDatabase) :
    RoomRepository {

    override fun getCategoriesWithChallenges(userId: String): List<Category> {
        val categoryDao = roomDatabase.getCategoryDao()
        return categoryDao.getCategoriesWithChallenges(roomDatabase, userId)
    }

    override fun insertCategoryWithChallenges(category: Category, userId: String) {
        val categoryDao = roomDatabase.getCategoryDao()
        categoryDao.insertCategoryWithChallenges(roomDatabase, category, userId)
    }
}