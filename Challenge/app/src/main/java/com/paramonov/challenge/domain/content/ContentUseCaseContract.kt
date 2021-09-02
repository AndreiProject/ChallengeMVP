package com.paramonov.challenge.domain.content

import com.paramonov.challenge.data.repository.model.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface ContentUseCaseContract {
    fun removeChallenges(categoryId: String, challengeId: String)
    fun getChallenges(categoryId: String, debounceMs: Long): Flowable<List<Challenge>>
    fun getAllCategories(): Flowable<List<Category>>
    fun getCategoriesWithChallenges(): Single<List<Category>>
    fun insertCategoryWithChallenges(category: Category)
    fun saveImg(imgURL: String)
}