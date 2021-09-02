package com.paramonov.challenge.ui.feature.category_list

import android.util.Log
import com.paramonov.challenge.domain.content.ContentUseCaseContract
import moxy.MvpPresenter
import com.paramonov.challenge.ui.feature.category_list.CategoryListPresenterContract.*
import io.reactivex.rxjava3.disposables.Disposable

class CategoryListPresenter(private val useCase: ContentUseCaseContract) : MvpPresenter<View>(), Presenter {
    companion object {
        private val TAG = CategoryListPresenter::class.java.simpleName
    }

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        disposable = useCase.getAllCategories().subscribe({
            viewState.updateList(it)
        }, {
            Log.e(TAG, it.toString())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}