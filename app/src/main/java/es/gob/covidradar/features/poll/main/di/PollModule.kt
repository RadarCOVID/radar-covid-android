package es.gob.covidradar.features.poll.main.di

import android.content.Context
import es.gob.covidradar.features.poll.main.presenter.PollPresenterImpl
import es.gob.covidradar.features.poll.main.protocols.PollPresenter
import es.gob.covidradar.features.poll.main.protocols.PollRouter
import es.gob.covidradar.features.poll.main.protocols.PollView
import es.gob.covidradar.features.poll.main.router.PollRouterImpl
import es.gob.covidradar.features.poll.main.view.PollActivity
import dagger.Module
import dagger.Provides

@Module
class PollModule {

    @Provides
    fun providesContext(activity: PollActivity): Context = activity

    @Provides
    fun providesView(activity: PollActivity): PollView = activity

    @Provides
    fun providesPresenter(presenter: PollPresenterImpl): PollPresenter = presenter

    @Provides
    fun providesRouter(router: PollRouterImpl): PollRouter = router

}