package org.ligi.kaxt

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.StringRes

fun Dialog.dismissIfShowing() {
    if (isShowing) {
        dismiss()
    }
}


fun Dialog?.dismissIfNotNullAndShowing() = this?.dismissIfShowing()

fun AlertDialog.setButton(whichButton: Int, @StringRes label: Int, action: () -> Unit) {
    setButton(whichButton, context.getString(label), { _: DialogInterface, _: Int ->
        action.invoke()
    })
}