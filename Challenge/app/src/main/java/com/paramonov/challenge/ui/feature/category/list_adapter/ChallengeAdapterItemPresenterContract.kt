package com.paramonov.challenge.ui.feature.category.list_adapter

import android.view.View
import com.paramonov.challenge.data.repository.model.Challenge

interface ChallengeAdapterItemPresenterContract {
    interface ItemPresenter <V> {
        fun bindView(view: V)
        fun getCount(): Int
        fun updateChallenges(list: List<Challenge>)
        fun getChallenge(pos: Int) : ChallengeSelection
        fun setItemSelection(pos: Int)
        fun getSelectionChallenges() : List<Challenge>
        fun resetSelectionChallenges()
    }

    interface ItemView {
        var pos: Int

        fun setItemSelection(isActive: Boolean)
        fun setRating(rating: String)
        fun setChallenge(challengeName: String)
        fun loadImgByUrl(imgUrl: String)
    }

    interface ItemListener {
        fun onClick(v: View?, item: Challenge, pos: Int)
        fun onLongClick(v: View?, pos: Int): Boolean
    }

    class ChallengeSelection(var isSelection: Boolean, val item: Challenge)
}