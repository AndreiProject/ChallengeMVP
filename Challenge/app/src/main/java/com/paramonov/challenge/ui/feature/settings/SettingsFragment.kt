package com.paramonov.challenge.ui.feature.settings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentSettingsBinding
import com.paramonov.challenge.domain.profile.*
import com.paramonov.challenge.ui.utils.warnError
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class SettingsFragment : MvpAppCompatFragment(), SettingsPresenterContract.View {
    private var binding: FragmentSettingsBinding? = null
    private val mBinding get() = binding!!

    companion object {
        fun newInstance() = SettingsFragment()
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}