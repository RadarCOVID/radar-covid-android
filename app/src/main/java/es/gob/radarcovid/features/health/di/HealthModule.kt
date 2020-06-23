package es.gob.radarcovid.features.health.di

import es.gob.radarcovid.features.health.presenter.HealthPresenterImpl
import es.gob.radarcovid.features.health.protocols.HealthPresenter
import es.gob.radarcovid.features.health.protocols.HealthView
import es.gob.radarcovid.features.health.view.HealthFragment
import dagger.Module
import dagger.Provides

@Module
class HealthModule {

    @Provides
    fun providesView(fragment: HealthFragment): HealthView = fragment

    @Provides
    fun providesPresenter(presenter: HealthPresenterImpl): HealthPresenter = presenter

}