package com.paramonov.challenge.data.repository.remote.firebase

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import io.reactivex.rxjava3.core.*

interface FirebaseRepository {
    fun checkAuth(): Boolean
    fun auth(email: String, password: String): Single<Boolean>
    fun getEmail() : String
    fun logOut()
    fun updateUser(user: User)
    fun getUser(): Flowable<User>
    fun removeChallenges(categoryId: String, challengeId: String)
    fun getChallenges(categoryId: String): LiveData<List<Challenge>>
    fun getAllCategories(): LiveData<List<Category>>
}