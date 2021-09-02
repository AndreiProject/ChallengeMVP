package com.paramonov.challenge.ui.feature.settings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.navigation.NavController
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentSettingsBinding
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.domain.profile.*
import com.paramonov.challenge.ui.utils.warnError
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class SettingsFragment : MvpAppCompatFragment(), SettingsPresenterContract.View,
    NavigationView.Item {
    private var binding: FragmentSettingsBinding? = null
    private val mBinding get() = binding!!

    private val useCase: ProfileUseCaseContract by inject(ProfileUseCase::class.java)
    private val presenter: SettingsPresenterContract.Presenter by moxyPresenter {
        SettingsPresenter(useCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
        }.root

    override fun init() {
        with(mBinding) {
            update.setOnClickListener {
                presenter.updateUser(name.text.toString(), surname.text.toString())
            }
            name.setOnEditorActionListener { _, _, _ ->
                return@setOnEditorActionListener false
            }
            surname.setOnEditorActionListener { _, _, _ ->
                return@setOnEditorActionListener false
            }
        }
    }

    override fun nameWarnError() {
        mBinding.name.warnError(requireContext(), R.string.name_input_warning)
    }

    override fun surnameWarnError() {
        mBinding.surname.warnError(requireContext(), R.string.surname_input_warning)
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun setName(name: String) {
        mBinding.name.setText(name)
    }

    override fun setSurname(surname: String) {
        mBinding.surname.setText(surname)
    }

    override fun navigateToStatistics() {
        getNavController()?.navigate(R.id.action_settingsFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCollection() {
        getNavController()?.navigate(R.id.action_settingsFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController()?.navigate(R.id.action_settingsFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController()?.navigate(R.id.action_settingsFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {}

    private fun getNavController(): NavController? {
        return (activity as? NavigationView.ControllerProvider)?.getNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}