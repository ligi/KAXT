package org.ligi.kaxt

import android.app.Dialog

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