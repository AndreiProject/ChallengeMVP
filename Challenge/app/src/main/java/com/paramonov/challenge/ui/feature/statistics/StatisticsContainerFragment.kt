package com.paramonov.challenge.ui.feature.statistics

import android.view.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentStatisticsContainerBinding
import com.paramonov.challenge.ui.feature.main.ToolbarContract

class StatisticsContainerFragment : Fragment() {
    private var binding: FragmentStatisticsContainerBinding? = null
    private val mBinding get() = binding!!

    companion object {
        fun newInstance() = StatisticsContainerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentStatisticsContainerBinding.inflate(layoutInflater, container, false)
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

            val root = requireActivity() as? ToolbarContract
            root?.setTitleToolbar(R.string.nav_statistics)
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewPager?.adapter = null
        binding = null
    }
}