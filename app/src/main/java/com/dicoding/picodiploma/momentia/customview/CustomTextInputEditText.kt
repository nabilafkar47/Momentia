package com.dicoding.picodiploma.momentia.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.dicoding.picodiploma.momentia.R
import com.google.android.material.textfield.TextInputEditText

class CustomTextInputEditText :
    TextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }

    init {
        addPasswordLengthValidator()
    }

    private fun addPasswordLengthValidator() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                when (inputType) {
                    EMAIL -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                            setError(context.getString(R.string.invalid_email_format), null)
                        } else {
                            error = null
                        }
                    }
                    PASSWORD -> {
                        if (input.length < 8) {
                            setError(context.getString(R.string.password_length_error), null)
                        } else {
                            error = null
                        }
                    }
                    else -> error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}