package com.paramonov.challenge.ui.feature.settings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.paramonov.challenge.App
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentSettingsBinding
import com.paramonov.challenge.domain.profile.*
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import com.paramonov.challenge.ui.utils.warnError
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import com.paramonov.challenge.ui.feature.settings.SettingsPresenterContract.View
import com.paramonov.challenge.ui.feature.settings.SettingsPresenterContract.Presenter

class SettingsFragment : MvpAppCompatFragment(), View {
    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var binding: FragmentSettingsBinding? = null
    private val mBinding get() = binding!!

    private val presenter: Presenter by moxyPresenter {
        SettingsPresenter().apply {
            App.appComponent.inject(this)
        }
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
        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_settings)
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