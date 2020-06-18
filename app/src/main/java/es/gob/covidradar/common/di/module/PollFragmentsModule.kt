package es.gob.covidradar.common.di.module

import es.gob.covidradar.common.di.scope.PerFragment
import es.gob.covidradar.features.poll.question.di.QuestionModule
import es.gob.covidradar.features.poll.question.view.QuestionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PollFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [QuestionModule::class])
    abstract fun bindsQuestionFragment(): QuestionFragment

}