package com.indra.coronaradar.features.poll.di

import com.indra.coronaradar.features.poll.presenter.PollPresenterImpl
import com.indra.coronaradar.features.poll.protocols.PollPresenter
import com.indra.coronaradar.features.poll.protocols.PollView
import com.indra.coronaradar.features.poll.view.PollActivity
import dagger.Module
import dagger.Provides

@Module
class PollModule {

    @Provides
    fun provideView(activity: PollActivity): PollView = activity

    @Provides
    fun providePresenter(presenter: PollPresenterImpl): PollPresenter = presenter

}