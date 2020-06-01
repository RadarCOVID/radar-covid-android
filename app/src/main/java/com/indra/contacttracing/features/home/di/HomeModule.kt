package com.indra.contacttracing.features.home.di

import com.indra.contacttracing.features.home.presenter.HomePresenterImpl
import com.indra.contacttracing.features.home.protocols.HomePresenter
import com.indra.contacttracing.features.home.protocols.HomeView
import com.indra.contacttracing.features.home.view.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun providesView(fragment: HomeFragment): HomeView = fragment

    @Provides
    fun providesPresenter(presenter: HomePresenterImpl): HomePresenter = presenter

}