package com.indra.contacttracing.common.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import com.indra.contacttracing.R
import kotlinx.android.synthetic.main.view_code_edittext.view.*


class CodeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var editTexts: Array<EditText>

    private var currentFocusIndex: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.view_code_edittext, this)
        editTexts = arrayOf(
            editTextCode1,
            editTextCode2,
            editTextCode3,
            editTextCode4,
            editTextCode5,
            editTextCode6,
            editTextCode7,
            editTextCode8,
            editTextCode9,
            editTextCode10
        )

        setOnClickListener {
            editTexts[currentFocusIndex].requestFocus()
            showKeyboard(context, editTexts[currentFocusIndex])
        }

        editTexts.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let { text ->
                        if (text.length == 1)
                            if (index < editTexts.size - 1)
                                editTexts[++currentFocusIndex].requestFocus()

                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            editText.setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                    if (index > 0) {
                        editTexts[index + -1].apply { setText("") }.requestFocus()
                        currentFocusIndex--
                    }
                    true
                } else {
                    false
                }
            }

            editText.setOnTouchListener { _, _ ->
                editTexts[currentFocusIndex].requestFocus()
                showKeyboard(context, editTexts[currentFocusIndex])
                true
            }
        }
    }

    private fun showKeyboard(context: Context, editText: EditText) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.toggleSoftInputFromWindow(
            editText.windowToken,
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    public fun getText(): String = editTexts.joinToString { it.text.toString() }

}