package com.indra.coronaradar.features.health.di

import com.indra.coronaradar.features.health.presenter.HealthPresenterImpl
import com.indra.coronaradar.features.health.protocols.HealthPresenter
import com.indra.coronaradar.features.health.protocols.HealthView
import com.indra.coronaradar.features.health.view.HealthFragment
import dagger.Module
import dagger.Provides

@Module
class HealthModule {

    @Provides
    fun providesView(fragment: HealthFragment): HealthView = fragment

    @Provides
    fun providesPresenter(presenter: HealthPresenterImpl): HealthPresenter = presenter

}