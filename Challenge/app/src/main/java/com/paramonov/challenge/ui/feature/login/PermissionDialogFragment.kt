package com.paramonov.challenge.ui.feature.login

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.paramonov.challenge.R

class PermissionDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.permission_title_dialog))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                dismiss()
                (activity as? DialogListener)?.run {
                    positiveClick()
                }
            }
            .setMessage(getString(R.string.message_permission_dialog))
        return builder.create()
    }

    interface DialogListener {
        fun positiveClick()
    }
}