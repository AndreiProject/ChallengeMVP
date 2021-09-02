package com.paramonov.challenge.ui.feature.login

import com.paramonov.challenge.domain.authorization.AuthorizationUseCaseContract
import com.paramonov.challenge.ui.feature.login.LoginPresenterContract.*
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class LoginPresenter(private val useCase: AuthorizationUseCaseContract) : MvpPresenter<View>(),
    Presenter {
    private var dataAuth: Result? = null
    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        if (useCase.checkAuth()) {
            dataAuth = Result.Authorization(true)
            viewState.navigateToMainActivity()
        } else {
            dataAuth = Result.Authorization(false)
        }
    }

    override fun auth(email: String, password: String) {
        if (email.isBlank()) {
            viewState.loginWarnError()
            return
        }
        if (password.isBlank()) {
            viewState.passwordWarnError()
            return
        }
        disposable?.dispose()
        disposable = useCase.auth(email, password).subscribe(
            {
                dataAuth = Result.Authorization(true)
                viewState.navigateToMainActivity()
            },
            {
                dataAuth = Result.Error(it)
                viewState.showToast(it.message ?: "")
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}