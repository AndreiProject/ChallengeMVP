package com.paramonov.challenge.ui.feature.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentPlannerBinding
import com.paramonov.challenge.ui.feature.main.ToolbarContract

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
            val root = requireActivity() as? ToolbarContract
            root?.setTitleToolbar(R.string.nav_planner)
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}