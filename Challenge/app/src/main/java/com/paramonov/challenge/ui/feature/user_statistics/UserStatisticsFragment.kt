package com.paramonov.challenge.ui.feature.user_statistics

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.paramonov.challenge.databinding.FragmentUserStatisticsBinding

class UserStatisticsFragment : Fragment() {
    private var binding: FragmentUserStatisticsBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserStatisticsBinding.inflate(layoutInflater, container, false).also {
        binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}