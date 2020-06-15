package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.common.di.scope.PerFragment
import com.indra.coronaradar.features.poll.question.di.QuestionModule
import com.indra.coronaradar.features.poll.question.view.QuestionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PollFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [QuestionModule::class])
    abstract fun bindsQuestionFragment(): QuestionFragment

}