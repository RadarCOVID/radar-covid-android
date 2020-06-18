package es.gob.covidradar.common.view

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import es.gob.covidradar.R
import kotlinx.android.synthetic.main.view_cmcheckbox.view.*

class CMCheckBox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var isChecked: Boolean = false
        set(value) {
            field = value
            checkBox.isChecked = value
        }
        get() = checkBox.isChecked

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_cmcheckbox, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CMCheckBox)
        val text = typedArray.getText(R.styleable.DottedTextView_android_text)
        typedArray.recycle()
        textView.text = text
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkBox.setOnCheckedChangeListener(listener)
    }

    fun setText(text: CharSequence?) {
        textView.text = text
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = ContextCompat.getColor(context, R.color.black_28)
    }

}