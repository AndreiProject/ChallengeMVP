package com.paramonov.challenge.ui.feature.category

import androidx.lifecycle.*
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.domain.content.*

class CategoryViewModel(
    private val category: Category,
    private val useCase: ContentUseCaseContract
) : ViewModel() {

    private val _challenges = MutableLiveData<List<Challenge>>()
    val challenges: LiveData<List<Challenge>> = _challenges

    private var repositoryChallenges = useCase.getChallenges(category.id)

    private val observer = Observer<List<Challenge>> { challenges ->
        _challenges.value = challenges
    }

    init {
        repositoryChallenges.observeForever(observer)
    }

    fun saveChallenge(challenge: Challenge) {
        category.items = listOf(challenge)
        useCase.insertCategoryWithChallenges(category)
    }

    fun saveCategoryImg() {
        useCase.saveImg(category.imgUrl)
    }

    fun removeChallenges(challenges: List<Challenge>) {
        for (item in challenges) {
            useCase.removeChallenges(category.id, item.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repositoryChallenges.removeObserver(observer)
    }
}