package com.paramonov.challenge.ui.feature.category

import android.util.Log
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.domain.content.ContentUseCaseContract
import com.paramonov.challenge.ui.feature.category.CategoryPresenterContract.*
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeItemPresenter
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class CategoryPresenter(
    private val category: Category,
    private val useCase: ContentUseCaseContract
) : MvpPresenter<View>(), Presenter {

    companion object {
        private val TAG = CategoryPresenter::class.java.simpleName
    }

    val itemPresenter = ChallengeItemPresenter()
    private var disposable: Disposable? = null
    var isActionSelectedItemChallenges = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        disposable = useCase.getChallenges(category.id, 500)
            .subscribe({
                if (it.isNotEmpty()) {
                    itemPresenter.updateChallenges(it)
                    viewState.updateAdapterViewChallenges()
                } else {
                    viewState.onBackToCategoryList()
                }
            }, {
                Log.e(TAG, it.toString())
            })
    }

    override fun removeSelectionChallenges() {
        val items = itemPresenter.getSelectionChallenges()
        for (item in items) {
            useCase.removeChallenges(category.id, item.id)
        }
    }

    override fun onClickViewChallengeItem(v: Any?, item: Challenge, pos: Int) {
        if (isActionSelectedItemChallenges) {
            itemPresenter.setItemSelection(pos)
            viewState.updateAdapterViewItemChallenges(pos)
        } else {
            v?.let {
                viewState.showPopup(it, item)
            }
        }
    }

    override fun onLongClickViewChallengeItem(pos: Int): Boolean {
        if (!isActionSelectedItemChallenges) {
            isActionSelectedItemChallenges = true
            viewState.showSelectedItemChallenges()
            itemPresenter.setItemSelection(pos)
            viewState.updateAdapterViewItemChallenges(pos)
            return true
        }
        return false
    }

    override fun hideSelectedItemChallenges() {
        isActionSelectedItemChallenges = false
        itemPresenter.resetSelectionChallenges()
        viewState.updateAdapterViewChallenges()
    }

    override fun saveChallenge(challenge: Challenge) {
        category.items = listOf(challenge)
        useCase.insertCategoryWithChallenges(category)
    }

    override fun saveCategoryImg() {
        useCase.saveImg(category.imgUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}