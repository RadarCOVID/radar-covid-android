/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.text.isDigitsOnly
import es.gob.radarcovid.R
import kotlinx.android.synthetic.main.view_code_edittext.view.*


class CodeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    companion object {
        private const val NON_VALID_CHARACTER = "-"
    }

    private var editTexts: Array<DeleteDetectionEditText>

    var textChangedListener: ((String) -> Unit)? = null

    var previousText: String = ""

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
            editTextCode10,
            editTextCode11,
            editTextCode12
        )

        setOnClickListener {
            focusLastEditText()
        }

        editTexts.forEachIndexed { index, editText ->

            editText.filters = arrayOf(object : InputFilter {
                override fun filter(
                    source: CharSequence?,
                    start: Int,
                    end: Int,
                    dest: Spanned?,
                    dstart: Int,
                    dend: Int
                ): CharSequence {
                    return if (source?.isDigitsOnly() == true) {
                        source
                    } else {
                        NON_VALID_CHARACTER
                    }
                }
            }, InputFilter.LengthFilter(1))

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let { text ->
                        if (text.toString() == NON_VALID_CHARACTER) {
                            editText.setText("")
                        } else if (text.length == 1) {
                            if (index < editTexts.size - 1) {
                                editTexts[index + 1].requestFocus()
                            }
                        }
                        if (text.toString() != NON_VALID_CHARACTER) {
                            if (getText() != previousText) {
                                previousText = getText()
                                textChangedListener?.invoke(getText())
                            }
                        }
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

            editText.onDeleteButtonClickListener = {
                if (index > 0) {
                    if (editText.text?.isEmpty() == true) {
                        editTexts[index - 1].apply { setText("") }.requestFocus()
                    } else {
                        editText.setText("")
                    }
                }
            }

            editText.setOnTouchListener { _, _ ->
                focusLastEditText()
                true
            }
        }
    }

    private fun focusLastEditText() {
        editTexts.find { it.text.isNullOrEmpty() || editTexts.indexOf(it) == editTexts.lastIndex }
            ?.let {
                it.requestFocus()
                showKeyboard(context, it)
            }
    }

    private fun showKeyboard(context: Context, editText: EditText) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.toggleSoftInputFromWindow(
            editText.windowToken,
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun getText(): String = editTexts.map { it.text }.joinToString("")

}