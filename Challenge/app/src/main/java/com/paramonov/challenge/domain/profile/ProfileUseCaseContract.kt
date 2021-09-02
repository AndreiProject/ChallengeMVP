package com.paramonov.challenge.domain.profile

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import io.reactivex.rxjava3.core.Flowable

interface ProfileUseCaseContract {
    fun updateUser(user: User)
    fun getUser(): Flowable<User>
}