package es.gob.radarcovid.common.di.module

import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.poll.question.di.QuestionModule
import es.gob.radarcovid.features.poll.question.view.QuestionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PollFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [QuestionModule::class])
    abstract fun bindsQuestionFragment(): QuestionFragment

}