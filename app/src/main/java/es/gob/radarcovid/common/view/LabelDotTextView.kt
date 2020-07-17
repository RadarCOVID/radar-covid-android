package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.usecase.GetLabelUseCase
import kotlinx.android.synthetic.main.view_dotted_textview.view.*
import javax.inject.Inject


class LabelDotTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        orientation = HORIZONTAL
        LayoutInflater.from(context)
            .inflate(R.layout.view_dotted_textview, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelDotTextView)
        val labelId = typedArray.getString(R.styleable.LabelDotTextView_labelId)
        val defaultText = typedArray.getText(R.styleable.LabelDotTextView_android_text)
        val dotVisibility = typedArray.getInt(R.styleable.LabelDotTextView_dotVisibility, 0)
        typedArray.recycle()
        when (dotVisibility) {
            0 -> imageViewDot.visibility = View.VISIBLE
            1 -> imageViewDot.visibility = View.INVISIBLE
            2 -> imageViewDot.visibility = View.GONE
        }
        textView.text = getLabelUseCase.getText(labelId, defaultText)
    }

    @Inject
    lateinit var getLabelUseCase: GetLabelUseCase

    fun setText(labelId: String?, resId: Int) {
        setText(labelId, context.getString(resId))
    }

    fun setText(labelId: String?, text: CharSequence, type: TextView.BufferType) {
        textView.setText(getLabelUseCase.getText(labelId, text), type)
    }

    fun setText(labelId: String?, defaultText: CharSequence) {
        textView.text = getLabelUseCase.getText(labelId, defaultText)
    }
}