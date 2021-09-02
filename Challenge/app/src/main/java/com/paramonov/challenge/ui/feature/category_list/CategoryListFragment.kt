package com.paramonov.challenge.ui.feature.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.databinding.FragmentCategoryListBinding
import com.paramonov.challenge.domain.content.*
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.ui.feature.category_list.CategoryListPresenterContract.*
import com.paramonov.challenge.ui.feature.category_list.CategoryListAdapter.ItemListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

const val CATEGORY_ID = "CATEGORY_ID"
const val CATEGORY_NAME = "CATEGORY_TITLE"
const val CATEGORY_IMG_URL = "CATEGORY_IMG_URL"

class CategoryListFragment : MvpAppCompatFragment(), NavigationView.Item, View, ItemListener {
    private var binding: FragmentCategoryListBinding? = null
    private val mBinding get() = binding!!
    private lateinit var rvCategories: RecyclerView

    private val useCase: ContentUseCaseContract by inject(ContentUseCase::class.java)
    private val presenter: Presenter by moxyPresenter {
        CategoryListPresenter(useCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoryListBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
        }.root

    override fun init() {
        rvCategories = mBinding.categoryRv
        rvCategories.adapter = CategoryListAdapter(this)
    }

    override fun updateList(list: List<Category>) {
        (rvCategories.adapter as CategoryListAdapter).let { adapter ->
            adapter.categories = list
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(category: Category) {
        val bundle = Bundle()
        with(bundle) {
            putString(CATEGORY_ID, category.id)
            putString(CATEGORY_NAME, category.name)
            putString(CATEGORY_IMG_URL, category.imgUrl)
        }
        getNavController()?.navigate(R.id.action_categoryListFragment_to_categoryFragment, bundle)
    }

    override fun navigateToStatistics() {
        getNavController()?.navigate(R.id.action_categoryListFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCollection() {
        getNavController()?.navigate(R.id.action_categoryListFragment_to_collectionFragment)
    }

    override fun navigateToPlanner() {
        getNavController()?.navigate(R.id.action_categoryListFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {
        getNavController()?.navigate(R.id.action_categoryListFragment_to_settingsFragment)
    }

    override fun navigateToCategoryList() {}

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvCategories.adapter = null
        binding = null
    }
}