package com.paramonov.challenge.ui.feature.category

import android.os.*
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.view.ActionMode.Callback
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.databinding.FragmentCategoriesBinding
import com.paramonov.challenge.ui.feature.category.ChallengeAdapter.ChallengeSelection
import com.paramonov.challenge.ui.feature.category_list.*
import com.paramonov.challenge.ui.feature.main.*
import com.paramonov.challenge.ui.utils.loadByUrl
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val START_DELAY = 500L

class CategoryFragment : Fragment(), NavigationView.Item, ChallengeAdapter.ItemListener {
    private var binding: FragmentCategoriesBinding? = null
    private val mBinding get() = binding!!
    private val handler = Handler(Looper.getMainLooper())

    private var actionMode: ActionMode? = null
    private lateinit var rvChallenges: RecyclerView

    private val model: CategoryViewModel by viewModel { parametersOf(getCategoryBundle()) }
    private val observer = Observer<List<Challenge>> { category ->
        if (category != null) {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({ updateUI(category) }, START_DELAY)
        } else {
            getNavController()?.popBackStack()
        }
    }

    private fun updateUI(challenges: List<Challenge>) {
        getChallengeAdapter()?.let { adapter ->
            val challengesSelection = mutableListOf<ChallengeSelection>()
            for (item in challenges) {
                challengesSelection.add(ChallengeSelection(false, item))
            }
            adapter.challenges = challengesSelection
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?, item: ChallengeSelection, pos: Int) {
        if (actionMode != null) {
            getChallengeAdapter()?.setItemSelection(item, pos)
        } else {
            v?.let { showPopup(it, item.challenge) }
        }
    }

    override fun onLongClick(item: ChallengeSelection, pos: Int): Boolean {
        if (actionMode == null) {
            actionMode = (activity as? AppCompatActivity)?.startSupportActionMode(callback)
            getChallengeAdapter()?.setItemSelection(item, pos)
            return true
        }
        return false
    }

    private fun showPopup(view: View, challenge: Challenge) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.popap_menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    model.saveChallenge(challenge)
                    true
                }
                R.id.menu_planner -> true
                else -> false
            }
        }
        popup.show()
    }

    private val callback = object : Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.menu_delete -> {
                    getChallengeAdapter()?.run {
                        val items = challenges.filter { it.selection }.map { it.challenge }
                        model.removeChallenges(items)
                    }
                }
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
            getChallengeAdapter()?.run {
                for (item in challenges) {
                    item.selection = false
                }
                notifyDataSetChanged()
            }
            actionMode = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoriesBinding.inflate(layoutInflater, container, false).also {
        binding = it

        rvChallenges = mBinding.challengeRv
        rvChallenges.adapter = ChallengeAdapter(requireContext(), this)

        mBinding.imgCover.loadByUrl(getStringArg(CATEGORY_IMG_URL))
        mBinding.tvTitle.text = getStringArg(CATEGORY_NAME)

        mBinding.downloadImg.setOnClickListener { model.saveCategoryImg() }
        model.challenges.observe(viewLifecycleOwner, observer)
    }.root

    private fun getCategoryBundle(): Category {
        return Category(
            getStringArg(CATEGORY_ID),
            getStringArg(CATEGORY_IMG_URL),
            getStringArg(CATEGORY_NAME)
        )
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

    private fun getChallengeAdapter(): ChallengeAdapter? {
        return (rvChallenges.adapter as? ChallengeAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        model.challenges.removeObserver(observer)
        rvChallenges.adapter = null
        binding = null
    }
}