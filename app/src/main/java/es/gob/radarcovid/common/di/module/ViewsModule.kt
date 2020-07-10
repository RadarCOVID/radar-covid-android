package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.view.LabelButton
import es.gob.radarcovid.common.view.LabelTextView

@Module
abstract class ViewsModule {

    @ContributesAndroidInjector
    abstract fun bindsLabelTextView(): LabelTextView

    @ContributesAndroidInjector
    abstract fun bindsLabelButton(): LabelButton

}