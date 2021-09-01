package com.paramonov.challenge.ui.feature.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentCollectionBinding
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.ui.feature.main.NavigationView
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionFragment : Fragment(), NavigationView.Item {
    private var binding: FragmentCollectionBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvCollection: RecyclerView

    private val model: CollectionViewModel by viewModel()
    private val observer = Observer<List<Category>> {
        (rvCollection.adapter as CollectionAdapter).run {
            categories = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCollectionBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            rvCollection = mBinding.collectionRv
            rvCollection.setHasFixedSize(true)
            rvCollection.adapter = CollectionAdapter()

            model.categories.observe(viewLifecycleOwner, observer)
        }.root

    override fun navigateToStatistics() {
        getNavController()?.navigate(R.id.action_collectionFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCategoryList() {
        getNavController()?.navigate(R.id.action_collectionFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController()?.navigate(R.id.action_collectionFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {
        getNavController()?.navigate(R.id.action_collectionFragment_to_settingsFragment)
    }

    override fun navigateToCollection() {}

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.categories.removeObserver(observer)
        binding = null
    }
}