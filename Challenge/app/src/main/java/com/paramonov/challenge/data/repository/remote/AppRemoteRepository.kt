package com.paramonov.challenge.data.repository.remote

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.remote.firebase.FirebaseRepository
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.data.repository.remote.retrofit.RetrofitRepository
import com.paramonov.challenge.ui.feature.login.Result

class AppRemoteRepository(
    private val fbRepository: FirebaseRepository,
    private val rfRepository: RetrofitRepository
) : RemoteRepository {

    override fun checkAuth() = fbRepository.checkAuth()

    override fun auth(email: String, password: String): LiveData<Result> {
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

    override fun getChallenges(categoryId: String) = fbRepository.getChallenges(categoryId)

    override fun getAllCategories() = fbRepository.getAllCategories()

    override fun getFile(requestUrl: String) = rfRepository.getFile(requestUrl)
}