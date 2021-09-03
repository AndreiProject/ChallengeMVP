package com.paramonov.challenge.ui.feature.collection

import android.util.Log
import com.paramonov.challenge.domain.content.ContentUseCaseContract
import moxy.MvpPresenter
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenterContract.*
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class CollectionListPresenter() : MvpPresenter<View>(),
    Presenter {

    companion object {
        private val TAG = CollectionListPresenter::class.java.simpleName
    }

    @Inject lateinit var useCase: ContentUseCaseContract
    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        disposable = useCase.getCategoriesWithChallenges().subscribe(
            {
                viewState.updateList(it)
            },
            {
                Log.e(TAG, it.toString())
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}