package com.paramonov.challenge.ui.utils

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.paramonov.challenge.R
import com.squareup.picasso.Picasso
import java.io.File

fun ImageView.loadByUrl(imgUrl: String) {
    if (imgUrl.isBlank()) {
        Picasso.get().load(File(imgUrl)).error(R.drawable.navigation).into(this)
    } else {
        Picasso.get().load(imgUrl).error(R.drawable.navigation).into(this)
    }
}

fun EditText.isValidate(context: Context, warnMessageId: Int): Boolean {
    if (text.isBlank()) {
        requestFocus()
        val message = context.getString(warnMessageId)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}