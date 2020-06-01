package com.indra.contacttracing.features.splash.di

import android.content.Context
import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.splash.presenter.SplashPresenterImpl
import com.indra.contacttracing.features.splash.protocols.SplashPresenter
import com.indra.contacttracing.features.splash.protocols.SplashRouter
import com.indra.contacttracing.features.splash.protocols.SplashView
import com.indra.contacttracing.features.splash.router.SplashRouterImpl
import com.indra.contacttracing.features.splash.view.SplashActivity
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun providesContext(activity: SplashActivity): Context = activity

    @Provides
    fun providesView(activity: SplashActivity): SplashView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: SplashPresenterImpl): SplashPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: SplashRouterImpl): SplashRouter = router

}