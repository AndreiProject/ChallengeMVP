package com.paramonov.challenge.ui.feature.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.paramonov.challenge.databinding.FragmentPlannerBinding

class PlannerFragment : Fragment() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}