package com.paramonov.challenge.ui.feature.login

import androidx.lifecycle.*
import com.paramonov.challenge.domain.authorization.*

class LoginViewModel(private val useCase: AuthorizationUseCaseContract) : ViewModel() {

    private var dataAuth: LiveData<Result>? = null
    private val _dataResult: MutableLiveData<Result> = MutableLiveData()
    val dataResult: LiveData<Result> = _dataResult


    init {
        if (useCase.checkAuth()) {
            _dataResult.value = Result.Authorization(true)
        } else {
            _dataResult.value = Result.Authorization(false)
        }
    }

    private val observer = Observer<Result> { result ->
        _dataResult.value = result
    }

    fun auth(email: String, password: String) {
        dataAuth?.removeObserver(observer)

        dataAuth = useCase.auth(email, password)
        dataAuth?.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        dataAuth?.removeObserver(observer)
    }
}