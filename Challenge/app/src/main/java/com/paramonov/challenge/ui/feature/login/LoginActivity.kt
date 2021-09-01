package com.paramonov.challenge.ui.feature.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ActivityLoginBinding
import com.paramonov.challenge.ui.feature.login.PermissionDialogFragment.DialogListener
import com.paramonov.challenge.ui.feature.main.MainActivity
import com.paramonov.challenge.ui.utils.isValidate
import org.koin.android.viewmodel.ext.android.viewModel

private const val REQUEST_PERMISSIONS = 112

class LoginActivity : AppCompatActivity(), DialogListener {
    companion object {
        val TAG: String = LoginActivity::class.java.simpleName
    }

    private var binding: ActivityLoginBinding? = null
    private val mBinding get() = binding!!
    private lateinit var dialog: PermissionDialogFragment

    private val model: LoginViewModel by viewModel()
    private val modelObserver = Observer<Result> {
        when (it) {
            is Result.Authorization -> if (it.isOk) {
                navigateToMainActivity()
            }
            is Result.Error -> {
                val message= it.error.toString()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        dialog = PermissionDialogFragment()
        checkPermission()

        init()
    }

    private fun init() {
        model.dataResult.observe(this, modelObserver)

        mBinding.signIn.setOnClickListener {
            auth()
        }
        mBinding.login.setOnEditorActionListener { _, _, _ ->
            return@setOnEditorActionListener false
        }
        mBinding.password.setOnEditorActionListener { _, _, _ ->
            auth()
            return@setOnEditorActionListener true
        }
    }

    private fun auth() {
        with(mBinding) {
            if (login.isValidate(this@LoginActivity, R.string.login_input_warning)) {
                if (password.isValidate(this@LoginActivity, R.string.password_input_warning)) {
                    model.auth(login.text.toString(), password.text.toString())
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        // Разрешения, которые необходимо явно запрашивать у пользователя
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
        model.dataResult.removeObserver(modelObserver)
        binding = null
    }
}