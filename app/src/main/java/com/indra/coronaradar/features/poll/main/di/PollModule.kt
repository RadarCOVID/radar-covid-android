package com.indra.coronaradar.features.poll.main.di

import android.content.Context
import com.indra.coronaradar.features.poll.main.presenter.PollPresenterImpl
import com.indra.coronaradar.features.poll.main.protocols.PollPresenter
import com.indra.coronaradar.features.poll.main.protocols.PollRouter
import com.indra.coronaradar.features.poll.main.protocols.PollView
import com.indra.coronaradar.features.poll.main.router.PollRouterImpl
import com.indra.coronaradar.features.poll.main.view.PollActivity
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