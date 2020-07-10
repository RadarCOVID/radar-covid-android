package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.usecase.GetLabelUseCase
import javax.inject.Inject

class LabelTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LabelTextView,
            0, 0
        ).apply {
            try {
                val labelId = getString(R.styleable.LabelTextView_labelId)
                val defaultText = getText(R.styleable.LabelTextView_android_text)
                text = getLabelUseCase.getText(labelId, defaultText)
            } finally {
                recycle()
            }
        }

    }

    @Inject
    lateinit var getLabelUseCase: GetLabelUseCase

    fun setText(labelId: String?, resId: Int) {
        setText(labelId, context.getString(resId))
    }

    fun setText(labelId: String?, defaultText: CharSequence) {
        text = getLabelUseCase.getText(labelId, defaultText)
    }

}