package org.ligi.kaxt

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.annotation.StringRes

fun Dialog.dismissIfShowing() {
    if (isShowing) {
        dismiss()
    }
}


fun Dialog?.dismissIfNotNullAndShowing() {
    if (this != null) {
        dismissIfShowing()
    }
}

fun AlertDialog.setButton(whichButton: Int, @StringRes label: Int, action: () -> Unit) {
    setButton(whichButton,context.getString(label),{ dialogInterface: DialogInterface, i: Int ->
        action.invoke()
    })
}