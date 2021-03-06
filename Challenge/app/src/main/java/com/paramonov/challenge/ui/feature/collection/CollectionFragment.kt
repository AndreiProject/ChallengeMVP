package com.paramonov.challenge.ui.feature.collection

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.App
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentCollectionBinding
import com.paramonov.challenge.data.repository.model.Category
import moxy.MvpAppCompatFragment
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenterContract.View
import com.paramonov.challenge.ui.feature.collection.CollectionListPresenterContract.Presenter
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import moxy.ktx.moxyPresenter

class CollectionFragment : MvpAppCompatFragment(), View {
    companion object {
        fun newInstance() = CollectionFragment()
    }

    private var binding: FragmentCollectionBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvCollection: RecyclerView

    private val presenter: Presenter by moxyPresenter {
        CollectionListPresenter().apply {
            App.appComponent.inject(this)
        }
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

        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_collection)
    }

    override fun updateList(list: List<Category>) {
        (rvCollection.adapter as CollectionAdapter).run {
            categories = list
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}