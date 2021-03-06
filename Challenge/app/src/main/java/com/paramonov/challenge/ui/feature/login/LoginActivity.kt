package com.paramonov.challenge.ui.feature.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.paramonov.challenge.App
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ActivityLoginBinding
import com.paramonov.challenge.domain.authorization.*
import com.paramonov.challenge.ui.feature.login.PermissionDialogFragment.DialogListener
import com.paramonov.challenge.ui.utils.warnError
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

private const val REQUEST_PERMISSIONS = 112

class LoginActivity : MvpAppCompatActivity(), DialogListener, LoginPresenterContract.View {
    companion object {
        val TAG: String = LoginActivity::class.java.simpleName
    }

    @Inject lateinit var navigatorHolder: NavigatorHolder
    private val navigator = AppNavigator(this, R.id.container)

    private var binding: ActivityLoginBinding? = null
    private val mBinding get() = binding!!
    private lateinit var dialog: PermissionDialogFragment

    private val presenter: LoginPresenterContract.Presenter by moxyPresenter {
        LoginPresenter().apply {
            App.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        App.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun init() {
        with(mBinding) {
            signIn.setOnClickListener {
                presenter.auth(login.text.toString(), password.text.toString())
            }
            login.setOnEditorActionListener { _, _, _ ->
                return@setOnEditorActionListener false
            }
            password.setOnEditorActionListener { _, _, _ ->
                presenter.auth(login.text.toString(), password.text.toString())
                return@setOnEditorActionListener true
            }
        }
        dialog = PermissionDialogFragment()
        dialog.apply {
            App.appComponent.inject(this)
        }
        checkPermission()
    }

    override fun loginWarnError() {
        mBinding.login.warnError(this@LoginActivity, R.string.login_input_warning)
    }

    override fun passwordWarnError() {
        mBinding.password.warnError(this@LoginActivity, R.string.password_input_warning)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission(): Boolean {
        // ????????????????????, ?????????????? ???????????????????? ???????? ?????????????????????? ?? ????????????????????????
        val dangerousPermissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        for (permission in dangerousPermissions) {
            if (isPermissionNeedConfirmation(permission)) {
                ActivityCompat.requestPermissions(
                    this,
                    dangerousPermissions,
                    REQUEST_PERMISSIONS
                )
                return false
            }
        }
        return true
    }

    private fun isPermissionNeedConfirmation(permission: String): Boolean {
        val permissionState = (ContextCompat.checkSelfPermission(this, permission))
        return permissionState != PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            val permissionNotConfirmed = grantResults.any {
                it != PackageManager.PERMISSION_GRANTED
            }
            if (permissionNotConfirmed) {
                dialog.show(supportFragmentManager, TAG)
            }
        }
    }

    override fun positiveClick() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}