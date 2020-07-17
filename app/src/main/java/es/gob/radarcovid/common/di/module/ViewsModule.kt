package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.view.LabelButton
import es.gob.radarcovid.common.view.LabelDotTextView
import es.gob.radarcovid.common.view.LabelTextView
import es.gob.radarcovid.common.view.QuestionEditText

@Module
abstract class ViewsModule {

    @ContributesAndroidInjector
    abstract fun bindsLabelDotTextView(): LabelDotTextView

    @ContributesAndroidInjector
    abstract fun bindsLabelTextView(): LabelTextView

    @ContributesAndroidInjector
    abstract fun bindsLabelButton(): LabelButton

    @ContributesAndroidInjector
    abstract fun bindsQuestionEditText(): QuestionEditText

}