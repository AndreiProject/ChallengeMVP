package com.paramonov.challenge.ui.feature.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.NavController
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentPlannerBinding
import com.paramonov.challenge.ui.feature.main.NavigationView

class PlannerFragment : Fragment(), NavigationView.Item {
    private var binding: FragmentPlannerBinding? = null
    private val mBinding get() = binding!!

    companion object {
        fun newInstance() = PlannerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPlannerBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
        }.root

    override fun navigateToStatistics() {
        getNavController()?.navigate(R.id.action_plannerFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCollection() {
        getNavController()?.navigate(R.id.action_plannerFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController()?.navigate(R.id.action_plannerFragment_to_categoryListFragment)
    }

    override fun navigateToSettings() {
        getNavController()?.navigate(R.id.action_plannerFragment_to_settingsFragment)
    }

    override fun navigateToPlanner() {}

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}