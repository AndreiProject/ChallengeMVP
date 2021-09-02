package com.paramonov.challenge.ui.feature.category_list

import com.paramonov.challenge.data.repository.model.Category
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface CategoryListPresenterContract {
    interface Presenter

    @AddToEndSingle
    interface View : MvpView {
        fun init()
        fun updateList(list: List<Category>)
    }
}