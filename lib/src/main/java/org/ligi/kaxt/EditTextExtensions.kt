package org.ligi.kaxt

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.doAfterEdit(foo: (editable: Editable) -> Unit) = this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        foo(editable)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

})