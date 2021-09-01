package com.paramonov.challenge.ui.feature.category_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentCategoryListBinding
import com.paramonov.challenge.data.repository.model.Category
import org.koin.android.viewmodel.ext.android.viewModel
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.ui.feature.category_list.CategoryListAdapter.ItemListener

const val CATEGORY_ID = "CATEGORY_ID"
const val CATEGORY_NAME = "CATEGORY_TITLE"
const val CATEGORY_IMG_URL = "CATEGORY_IMG_URL"

class CategoryListFragment : Fragment(), NavigationView.Item, ItemListener {
    private var binding: FragmentCategoryListBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvCategories: RecyclerView

    private val model: CategoryListViewModel by viewModel()
    private val observer = Observer<List<Category>> { categories ->
        (rvCategories.adapter as CategoryListAdapter).let { adapter ->
            adapter.categories = categories
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoryListBinding.inflate(layoutInflater, container, false).also {
        binding = it
        rvCategories = mBinding.categoryRv
        rvCategories.adapter = CategoryListAdapter(this)

        model.categories.observe(viewLifecycleOwner, observer)
    }.root

    override fun onClick(category: Category) {
        val bundle = Bundle()
        with(bundle) {
            putString(CATEGORY_ID, category.id)
            putString(CATEGORY_NAME, category.name)
            putString(CATEGORY_IMG_URL, category.imgUrl)
        }
        getNavController()?.navigate(
            R.id.action_categoryListFragment_to_categoryFragment,
            bundle
        )
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
        model.categories.removeObserver(observer)
        rvCategories.adapter = null
        binding = null
    }
}