package com.paramonov.challenge.ui.feature.category.list_adapter

import com.paramonov.challenge.data.repository.model.Challenge
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeAdapterItemPresenterContract.*

class ChallengeItemPresenter : ItemPresenter<ItemView> {
    private var challenges = mutableListOf<ChallengeSelection>()

    override fun bindView(view: ItemView) {
        val item = challenges[view.pos]
        view.setItemSelection(item.isSelection)
        view.setRating(item.item.rating)
        view.setChallenge(item.item.name)
        view.loadImgByUrl(item.item.imgUrl)
    }

    override fun getCount() = challenges.size
    override fun getChallenge(pos: Int) = challenges[pos]

    override fun setItemSelection(pos: Int) {
        val item = challenges[pos]
        item.isSelection = !item.isSelection
    }

    override fun updateChallenges(list: List<Challenge>) {
        challenges.clear()
        for (item in list) {
            challenges.add(ChallengeSelection(false, item))
        }
    }

    override fun getSelectionChallenges(): List<Challenge> {
        return challenges
            .filter { it.isSelection }
            .map { it.item }
    }

    override fun resetSelectionChallenges() {
        for (item in challenges) {
            item.isSelection = false
        }
    }
}