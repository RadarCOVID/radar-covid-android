package com.indra.contacttracing.features.home.di

import android.content.Context
import com.indra.contacttracing.common.di.scope.PerFragment
import com.indra.contacttracing.features.home.presenter.HomePresenterImpl
import com.indra.contacttracing.features.home.protocols.HomePresenter
import com.indra.contacttracing.features.home.protocols.HomeRouter
import com.indra.contacttracing.features.home.protocols.HomeView
import com.indra.contacttracing.features.home.router.HomeRouterImpl
import com.indra.contacttracing.features.home.view.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun providesContext(fragment: HomeFragment): Context = fragment.context!!

    @Provides
    fun providesView(fragment: HomeFragment): HomeView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: HomePresenterImpl): HomePresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: HomeRouterImpl): HomeRouter = router

}