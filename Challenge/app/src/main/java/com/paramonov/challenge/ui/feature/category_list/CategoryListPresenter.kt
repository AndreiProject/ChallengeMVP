package com.paramonov.challenge.ui.feature.category_list

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.domain.content.ContentUseCaseContract
import moxy.MvpPresenter
import com.paramonov.challenge.ui.feature.category_list.CategoryListPresenterContract.*
import com.paramonov.challenge.ui.navigation.AndroidScreens
import com.paramonov.challenge.ui.navigation.IScreens
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.java.KoinJavaComponent

class CategoryListPresenter(private val useCase: ContentUseCaseContract) : MvpPresenter<View>(), Presenter {
    companion object {
        private val TAG = CategoryListPresenter::class.java.simpleName
    }

    private val router: Router by KoinJavaComponent.inject(Router::class.java)
    private val screens: IScreens by KoinJavaComponent.inject(AndroidScreens::class.java)

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

    override fun navigateToCategoryListFragment(category: Category) {
        router.navigateTo(screens.navigateToCategoryFragment(category))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
    }
}