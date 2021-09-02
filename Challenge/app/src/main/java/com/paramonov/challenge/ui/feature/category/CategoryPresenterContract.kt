package com.paramonov.challenge.ui.feature.category

import com.paramonov.challenge.data.repository.model.Challenge
import moxy.MvpView
import moxy.viewstate.strategy.alias.*

interface CategoryPresenterContract {
    interface Presenter {
        fun saveChallenge(challenge: Challenge)
        fun saveCategoryImg()
        fun removeSelectionChallenges()
        fun hideSelectedItemChallenges()
        fun onClickViewChallengeItem(v: Any?, item: Challenge, pos: Int)
        fun onLongClickViewChallengeItem(pos: Int) : Boolean
    }

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun onBackToCategoryList()
        fun showSelectedItemChallenges()
        fun updateAdapterViewChallenges()
        fun updateAdapterViewItemChallenges(pos: Int)
        @Skip
        fun showPopup(view: Any, item: Challenge)
    }
}