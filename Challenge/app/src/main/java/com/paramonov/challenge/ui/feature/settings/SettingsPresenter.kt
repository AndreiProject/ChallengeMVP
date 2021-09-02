package com.paramonov.challenge.ui.feature.settings

import android.util.Log
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.domain.profile.ProfileUseCaseContract
import com.paramonov.challenge.ui.feature.settings.SettingsPresenterContract.*
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class SettingsPresenter(private val useCase: ProfileUseCaseContract) : MvpPresenter<View>(),
    Presenter {
    companion object {
        val TAG: String = SettingsPresenter::class.java.simpleName
    }

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        disposable = useCase.getUser()
            .subscribe({
                viewState.setName(it.name)
                viewState.setSurname(it.surname)
            }, {
                Log.e(TAG, it.toString())
            })
    }

    override fun updateUser(name: String, surname: String) {
        useCase.updateUser(User(name, surname))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}