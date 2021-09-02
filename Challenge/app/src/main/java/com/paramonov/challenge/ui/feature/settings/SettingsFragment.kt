package com.paramonov.challenge.ui.feature.settings

import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentSettingsBinding
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.domain.profile.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent

class SettingsFragment : MvpAppCompatFragment(), SettingsPresenterContract.View,
    NavigationView.Item {
    private var binding: FragmentSettingsBinding? = null
    private val mBinding get() = binding!!

    private val useCase: ProfileUseCaseContract by KoinJavaComponent.inject(ProfileUseCase::class.java)
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
        mBinding.update.setOnClickListener {
            val name = mBinding.name.text.toString()
            val surname = mBinding.surname.text.toString()
            presenter.updateUser(name, surname)
        }
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