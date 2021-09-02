package com.paramonov.challenge.ui.feature.collection

import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentCollectionBinding
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.domain.content.*
import com.paramonov.challenge.ui.feature.main.NavigationView
import moxy.MvpAppCompatFragment
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenterContract.View
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenterContract.Presenter
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class CollectionFragment : MvpAppCompatFragment(), NavigationView.Item, View {
    private var binding: FragmentCollectionBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvCollection: RecyclerView

    private val useCase: ContentUseCaseContract by inject(ContentUseCase::class.java)
    private val presenter: Presenter by moxyPresenter {
        CollectionListPresenter(useCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCollectionBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
        }.root

    override fun init() {
        rvCollection = mBinding.collectionRv
        rvCollection.setHasFixedSize(true)
        rvCollection.adapter = CollectionAdapter()
    }

    override fun updateList(list: List<Category>) {
        (rvCollection.adapter as CollectionAdapter).run {
            categories = list
            notifyDataSetChanged()
        }
    }

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
        binding = null
    }
}