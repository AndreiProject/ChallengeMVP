package com.paramonov.challenge.data.repository.remote

import com.paramonov.challenge.data.repository.model.Challenge
import com.paramonov.challenge.data.repository.remote.firebase.FirebaseRepository
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.data.repository.remote.retrofit.RetrofitRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class AppRemoteRepository(
    private val fbRepository: FirebaseRepository,
    private val rfRepository: RetrofitRepository
) : RemoteRepository {

    override fun checkAuth() = fbRepository.checkAuth()

    override fun auth(email: String, password: String): Single<Boolean> {
        return fbRepository.auth(email, password)
    }

    override fun getEmail() = fbRepository.getEmail()

    override fun logOut() {
        fbRepository.logOut()
    }

    override fun updateUser(user: User) {
        fbRepository.updateUser(user)
    }

    override fun getUser() = fbRepository.getUser()

    override fun removeChallenges(categoryId: String, challengeId: String) {
        fbRepository.removeChallenges(categoryId, challengeId)
    }

    override fun getChallenges(categoryId: String, debounceMs: Long): Flowable<List<Challenge>> {
        return fbRepository.getChallenges(categoryId, debounceMs)
    }

    override fun getAllCategories() = fbRepository.getAllCategories()

    override fun getFile(requestUrl: String) = rfRepository.getFile(requestUrl)
}