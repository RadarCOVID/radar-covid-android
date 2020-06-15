package com.indra.coronaradar.features.poll.main.di

import com.indra.coronaradar.features.poll.main.presenter.PollPresenterImpl
import com.indra.coronaradar.features.poll.main.protocols.PollPresenter
import com.indra.coronaradar.features.poll.main.protocols.PollView
import com.indra.coronaradar.features.poll.main.view.PollActivity
import dagger.Module
import dagger.Provides

@Module
class PollModule {

    @Provides
    fun provideView(activity: PollActivity): PollView = activity

    @Provides
    fun providePresenter(presenter: PollPresenterImpl): PollPresenter = presenter

}