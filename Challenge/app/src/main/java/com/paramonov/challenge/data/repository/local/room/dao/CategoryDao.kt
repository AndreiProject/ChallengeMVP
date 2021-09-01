package com.paramonov.challenge.data.repository.local.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.data.repository.local.room.model.CategoryChallenges
import com.paramonov.challenge.data.source.room.AppRoomDatabase
import org.koin.java.KoinJavaComponent.inject

@Dao
abstract class CategoryDao {
    private val roomDatabase: AppRoomDatabase by inject(AppRoomDatabase::class.java)

    @Query(
        "select distinct category.id, category.img_url, category.name from category " +
                "join category_challenges on category_challenges.category_id = category.id " +
                "where category_challenges.user_id = :userId"
    )
    protected abstract fun getCategories(userId: String): List<Category>

    @Insert(onConflict = REPLACE)
    protected abstract fun insert(category: Category)

    @Delete
    abstract fun delete(category: Category)

    @Transaction
    open fun getCategoriesWithChallenges(userId: String): List<Category> {
        val categories = getCategories(userId)
        val challengeDao = roomDatabase.getChallengeDao()
        for (item in categories) {
            item.items = challengeDao.getCategoryChallenges(item.id, userId)
        }
        return categories
    }

    @Transaction
    open fun insertCategoryWithChallenges(category: Category, userId: String) {
        insert(category)
        category.items?.let { categories ->
            for (item in categories) {
                roomDatabase.getChallengeDao().insert(item)
                roomDatabase.getCategoryChallengesDao()
                    .insert(
                        CategoryChallenges(
                            userId,
                            category.id,
                            item.id
                        )
                    )
            }
        }
    }
}