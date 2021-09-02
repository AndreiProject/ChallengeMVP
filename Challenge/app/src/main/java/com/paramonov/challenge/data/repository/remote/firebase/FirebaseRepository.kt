package com.paramonov.challenge.data.repository.remote.firebase

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.ui.feature.login.Result
import io.reactivex.rxjava3.core.Flowable

interface FirebaseRepository {
    fun checkAuth(): Boolean
    fun auth(email: String, password: String): LiveData<Result>
    fun getEmail() : String
    fun logOut()
    fun updateUser(user: User)
    fun getUser(): Flowable<User>
    fun removeChallenges(categoryId: String, challengeId: String)
    fun getChallenges(categoryId: String): LiveData<List<Challenge>>
    fun getAllCategories(): LiveData<List<Category>>
}