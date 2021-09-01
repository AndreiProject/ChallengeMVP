package com.paramonov.challenge.ui.feature.view_pager_container_statistics_fragment

import android.view.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayoutMediator
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentViewPagerContainerBinding
import com.paramonov.challenge.ui.feature.main.NavigationView

class ViewPagerContainerStatisticsFragment : Fragment(), NavigationView.Item {
    private var binding: FragmentViewPagerContainerBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentViewPagerContainerBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            val viewPager = mBinding.viewPager
            viewPager.adapter = StatisticsAdapter(this)

            val tabLayout = mBinding.tabLayout
            val tabName = resources.getStringArray(R.array.tab_name_statistics)
            val tabImage = intArrayOf(
                R.drawable.ic_user_statistics,
                R.drawable.ic_all_statistics
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabName[position]
                tab.setIcon(tabImage[position])
            }.attach()
        }.root

    override fun navigateToCollection() {
        getNavController()?.navigate(R.id.action_generalStatisticsFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController()?.navigate(R.id.action_generalStatisticsFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController()?.navigate(R.id.action_generalStatisticsFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {
        getNavController()?.navigate(R.id.action_generalStatisticsFragment_to_settingsFragment)
    }

    override fun navigateToStatistics() {}

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewPager?.adapter = null
        binding = null
    }
}