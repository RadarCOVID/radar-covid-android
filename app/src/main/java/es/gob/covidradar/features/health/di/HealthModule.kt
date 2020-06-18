package es.gob.covidradar.features.health.di

import es.gob.covidradar.features.health.presenter.HealthPresenterImpl
import es.gob.covidradar.features.health.protocols.HealthPresenter
import es.gob.covidradar.features.health.protocols.HealthView
import es.gob.covidradar.features.health.view.HealthFragment
import dagger.Module
import dagger.Provides

@Module
class HealthModule {

    @Provides
    fun providesView(fragment: HealthFragment): HealthView = fragment

    @Provides
    fun providesPresenter(presenter: HealthPresenterImpl): HealthPresenter = presenter

}