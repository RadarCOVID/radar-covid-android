package com.indra.contacttracing.features.health.di

import com.indra.contacttracing.features.health.presenter.HealthPresenterImpl
import com.indra.contacttracing.features.health.protocols.HealthPresenter
import com.indra.contacttracing.features.health.protocols.HealthView
import com.indra.contacttracing.features.health.view.HealthFragment
import dagger.Module
import dagger.Provides

@Module
class HealthModule {

    @Provides
    fun providesView(fragment: HealthFragment): HealthView = fragment

    @Provides
    fun providesPresenter(presenter: HealthPresenterImpl): HealthPresenter = presenter

}