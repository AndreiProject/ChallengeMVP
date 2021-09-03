package com.paramonov.challenge.ui.feature.login

import com.github.terrakok.cicerone.Router
import com.paramonov.challenge.domain.authorization.AuthorizationUseCaseContract
import com.paramonov.challenge.ui.feature.login.LoginPresenterContract.*
import com.paramonov.challenge.ui.navigation.*
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject

class LoginPresenter() : MvpPresenter<View>(), Presenter {
    private var dataAuth: Result? = null
    private var disposable: Disposable? = null

    @Inject lateinit var useCase: AuthorizationUseCaseContract
    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        if (useCase.checkAuth()) {
            dataAuth = Result.Authorization(true)
            navigateToMainActivity()
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
                navigateToMainActivity()
            },
            {
                dataAuth = Result.Error(it)
                viewState.showToast(it.message ?: "")
            }
        )
    }

    private fun navigateToMainActivity() {
        router.replaceScreen(screens.navigateToMainActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}