package com.paramonov.challenge.ui.feature.collection

import android.util.Log
import androidx.lifecycle.*
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.domain.content.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class CollectionViewModel(useCase: ContentUseCaseContract) : ViewModel() {

    companion object {
        private val TAG = CollectionViewModel::class.java.simpleName
    }

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories
    private var categoriesSubscriber: Disposable? = null

    init {
        val observer = useCase.getCategoriesWithChallenges()
        categoriesSubscriber = observer.observeOn(AndroidSchedulers.mainThread()).subscribe({
            _categories.value = it
        }, {
            Log.e(TAG, it.toString())
        })
    }

    override fun onCleared() {
        categoriesSubscriber?.dispose()
    }
}