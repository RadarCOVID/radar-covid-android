package com.indra.contacttracing.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.indra.contacttracing.R
import kotlinx.android.synthetic.main.view_dotted_textview.view.*


class DottedTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context)
            .inflate(R.layout.view_dotted_textview, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DottedTextView)
        val text = typedArray.getText(R.styleable.DottedTextView_android_text)
        val dotVisibility = typedArray.getInt(R.styleable.DottedTextView_dotVisibility, 0)
        typedArray.recycle()
        when (dotVisibility) {
            0 -> imageViewDot.visibility = View.VISIBLE
            1 -> imageViewDot.visibility = View.INVISIBLE
            2 -> imageViewDot.visibility = View.GONE
        }
        textView.text = text
    }

    fun setText(text: CharSequence, type: TextView.BufferType) {
        textView.setText(text, type)
    }
}