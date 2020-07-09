package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.datamanager.usecase.GetLabelUseCase
import javax.inject.Inject

class LabelTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    init {
        (context.applicationContext as HasAndroidInjector)
            .androidInjector()
            .inject(this)
        getLabelUseCase.test()
    }

    @Inject
    lateinit var getLabelUseCase: GetLabelUseCase
}