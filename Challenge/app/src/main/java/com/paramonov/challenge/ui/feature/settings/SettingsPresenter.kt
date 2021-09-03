package com.paramonov.challenge.ui.feature.settings

import android.util.Log
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.domain.profile.ProfileUseCaseContract
import com.paramonov.challenge.ui.feature.settings.SettingsPresenterContract.*
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject

class SettingsPresenter() : MvpPresenter<View>(),
    Presenter {
    companion object {
        val TAG: String = SettingsPresenter::class.java.simpleName
    }

    @Inject lateinit var useCase: ProfileUseCaseContract
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
        if(name.isBlank()) {
            viewState.nameWarnError()
            return
        }
        if(surname.isBlank()) {
            viewState.surnameWarnError()
            return
        }
        useCase.updateUser(User(name, surname))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}