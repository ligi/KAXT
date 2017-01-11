package org.ligi.kaxt

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.doAfterEdit(function: (editable: Editable) -> Unit) = addTextChangedListener(object : TextWatcher {

    override fun afterTextChanged(editable: Editable) = function(editable)
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

})