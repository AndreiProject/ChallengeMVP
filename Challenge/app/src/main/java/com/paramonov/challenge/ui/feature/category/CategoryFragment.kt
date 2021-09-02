package com.paramonov.challenge.ui.feature.category

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.view.ActionMode.Callback
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.databinding.FragmentCategoriesBinding
import com.paramonov.challenge.domain.content.*
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeAdapter
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeAdapterItemPresenterContract
import com.paramonov.challenge.ui.feature.category_list.*
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.ui.utils.loadByUrl
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class CategoryFragment : MvpAppCompatFragment(), NavigationView.Item,
    CategoryPresenterContract.View, ChallengeAdapterItemPresenterContract.ItemListener {

    private var binding: FragmentCategoriesBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvChallenges: RecyclerView

    private val useCase: ContentUseCaseContract by inject(ContentUseCase::class.java)
    private val presenter: CategoryPresenter by moxyPresenter {
        CategoryPresenter(getCategoryBundle(), useCase)
    }

    private fun getCategoryBundle(): Category {
        return Category(
            getStringArg(CATEGORY_ID),
            getStringArg(CATEGORY_IMG_URL),
            getStringArg(CATEGORY_NAME)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
        }.root

    override fun init() {
        rvChallenges = mBinding.challengeRv
        rvChallenges.adapter = ChallengeAdapter(requireContext(), this, presenter.itemPresenter)

        mBinding.imgCover.loadByUrl(getStringArg(CATEGORY_IMG_URL))
        mBinding.tvTitle.text = getStringArg(CATEGORY_NAME)

        mBinding.downloadImg.setOnClickListener { presenter.saveCategoryImg() }
    }

    override fun updateAdapterViewChallenges() {
        getChallengeAdapter()?.notifyDataSetChanged()
    }

    override fun updateAdapterViewItemChallenges(pos: Int) {
        getChallengeAdapter()?.notifyItemChanged(pos)
    }

    override fun onBackToCategoryList() {
        getNavController()?.popBackStack()
    }

    override fun onClick(v: View?, item: Challenge, pos: Int) {
        presenter.onClickViewChallengeItem(v, item, pos)
    }

    override fun onLongClick(v: View?, pos: Int): Boolean {
        return presenter.onLongClickViewChallengeItem(pos)
    }

    override fun showPopup(view: Any, item: Challenge) {
        (view as? View)?.let { v ->
            val popup = PopupMenu(requireContext(), v)
            popup.inflate(R.menu.popap_menu)

            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_add -> {
                        presenter.saveChallenge(item)
                        true
                    }
                    R.id.menu_planner -> true
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun showSelectedItemChallenges() {
        if (presenter.isActionSelectedItemChallenges) {
            (activity as? AppCompatActivity)?.startSupportActionMode(callback)
        }
    }

    private val callback = object : Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.menu_delete -> presenter.removeSelectionChallenges()
            }
            mode?.finish()
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.run {
                menuInflater.inflate(R.menu.menu_action_toolbar_category, menu)
                return true
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            presenter.hideSelectedItemChallenges()
        }
    }

    private fun getChallengeAdapter(): ChallengeAdapter? {
        return (rvChallenges.adapter as? ChallengeAdapter)
    }

    private fun getStringArg(ket: String): String {
        return arguments?.getString(ket, "") ?: ""
    }

    override fun navigateToStatistics() {
        getNavController()?.navigate(R.id.action_categoryFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCollection() {
        getNavController()?.navigate(R.id.action_categoryFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController()?.navigate(R.id.action_categoryFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController()?.navigate(R.id.action_categoryFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {
        getNavController()?.navigate(R.id.action_categoryFragment_to_settingsFragment)
    }

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvChallenges.adapter = null
        binding = null
    }
}