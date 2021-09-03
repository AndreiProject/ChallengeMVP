package com.paramonov.challenge.ui.feature.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.App
import com.paramonov.challenge.R
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.databinding.FragmentCategoryListBinding
import com.paramonov.challenge.ui.feature.category_list.CategoryListPresenterContract.*
import com.paramonov.challenge.ui.feature.category_list.CategoryListAdapter.ItemListener
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CategoryListFragment : MvpAppCompatFragment(), View, ItemListener {
    companion object {
        fun newInstance() = CategoryListFragment()
    }

    private var binding: FragmentCategoryListBinding? = null
    private val mBinding get() = binding!!
    private lateinit var rvCategories: RecyclerView

    private val presenter: Presenter by moxyPresenter {
        CategoryListPresenter().apply {
            App.appComponent.inject(this)
        }
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

        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_category_list)
    }

    override fun updateList(list: List<Category>) {
        (rvCategories.adapter as CategoryListAdapter).let { adapter ->
            adapter.categories = list
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(category: Category) {
        presenter.navigateToCategoryListFragment(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvCategories.adapter = null
        binding = null
    }
}