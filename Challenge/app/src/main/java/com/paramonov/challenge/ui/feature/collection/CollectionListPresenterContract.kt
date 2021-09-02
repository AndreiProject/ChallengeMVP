package com.paramonov.challenge.ui.feature.collection

import com.paramonov.challenge.data.repository.model.Category
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface CollectionListPresenterContract {

    interface Presenter

    @AddToEndSingle
    interface View : MvpView{
        fun init()
        fun updateList(list: List<Category>)
    }
}