package com.paramonov.challenge.ui.feature.login

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.terrakok.cicerone.Router
import com.paramonov.challenge.R
import com.paramonov.challenge.ui.navigation.*
import org.koin.java.KoinJavaComponent.inject

class PermissionDialogFragment : DialogFragment() {
    private val router: Router by inject(Router::class.java)
    private val screens: IScreens by inject(AndroidScreens::class.java)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.permission_title_dialog))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                dismiss()
                router.navigateTo(screens.navigateToSettingsActivity(), true)
            }
            .setMessage(getString(R.string.message_permission_dialog))
        return builder.create()
    }

    interface DialogListener {
        fun positiveClick()
    }
}