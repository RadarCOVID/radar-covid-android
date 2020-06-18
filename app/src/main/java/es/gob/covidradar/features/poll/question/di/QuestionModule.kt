package es.gob.covidradar.features.poll.question.di

import es.gob.covidradar.common.di.scope.PerFragment
import es.gob.covidradar.features.poll.question.presenter.QuestionPresenterImpl
import es.gob.covidradar.features.poll.question.protocols.QuestionPresenter
import es.gob.covidradar.features.poll.question.protocols.QuestionView
import es.gob.covidradar.features.poll.question.view.QuestionFragment
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