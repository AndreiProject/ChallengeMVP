package com.paramonov.challenge.ui.feature.all_statistics_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.paramonov.challenge.databinding.FragmentAllStatisticsBinding

class AllStatisticsFragment : Fragment() {
    private var binding: FragmentAllStatisticsBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAllStatisticsBinding.inflate(layoutInflater, container, false).also {
        binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}