package com.paramonov.challenge.ui.feature.settings

import androidx.lifecycle.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.domain.profile.*

class SettingsViewModel(private val useCase: ProfileUseCaseContract) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var repositoryUser = useCase.getUser()

    private val observer = Observer<User> {
        _user.value = it
    }

    init {
        repositoryUser.observeForever(observer)
    }

    fun updateUser(user: User) {
        useCase.updateUser(user)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryUser.removeObserver(observer)
    }
}