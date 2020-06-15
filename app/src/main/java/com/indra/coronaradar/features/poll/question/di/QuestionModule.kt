package com.indra.coronaradar.features.poll.question.di

import com.indra.coronaradar.common.di.scope.PerFragment
import com.indra.coronaradar.features.poll.question.presenter.QuestionPresenterImpl
import com.indra.coronaradar.features.poll.question.protocols.QuestionPresenter
import com.indra.coronaradar.features.poll.question.protocols.QuestionView
import com.indra.coronaradar.features.poll.question.view.QuestionFragment
import dagger.Module
import dagger.Provides

@Module
class QuestionModule {

    @Provides
    fun providesView(fragment: QuestionFragment): QuestionView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: QuestionPresenterImpl): QuestionPresenter = presenter

}