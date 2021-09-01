package com.paramonov.challenge.ui.feature.category_list

import androidx.lifecycle.*
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.domain.content.*

class CategoryListViewModel(useCase: ContentUseCaseContract) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private var repositoryCategories = useCase.getAllCategories()

    private val observer = Observer<List<Category>> { list ->
        _categories.value = list
    }

    init {
        repositoryCategories.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryCategories.removeObserver(observer)
    }
}