package com.indra.coronaradar.features.splash.di

import android.content.Context
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.features.splash.presenter.SplashPresenterImpl
import com.indra.coronaradar.features.splash.protocols.SplashPresenter
import com.indra.coronaradar.features.splash.protocols.SplashRouter
import com.indra.coronaradar.features.splash.protocols.SplashView
import com.indra.coronaradar.features.splash.router.SplashRouterImpl
import com.indra.coronaradar.features.splash.view.SplashActivity
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