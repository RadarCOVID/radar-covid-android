package es.gob.covidradar.features.poll.completed.di

import dagger.Module
import dagger.Provides
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.features.poll.completed.presenter.PollCompletedPresenterImpl
import es.gob.covidradar.features.poll.completed.protocols.PollCompletedPresenter
import es.gob.covidradar.features.poll.completed.protocols.PollCompletedView
import es.gob.covidradar.features.poll.completed.view.PollCompletedActivity

@Module
class PollCompletedModule {

    @Provides
    @PerActivity
    fun providesView(activity: PollCompletedActivity): PollCompletedView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: PollCompletedPresenterImpl): PollCompletedPresenter = presenter

}