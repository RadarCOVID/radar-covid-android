package com.indra.coronaradar.features.main.di

import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.features.main.presenter.MainPresenterImpl
import com.indra.coronaradar.features.main.protocols.MainPresenter
import com.indra.coronaradar.features.main.protocols.MainRouter
import com.indra.coronaradar.features.main.protocols.MainView
import com.indra.coronaradar.features.main.router.MainRouterImpl
import com.indra.coronaradar.features.main.view.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providesActivity(activity: MainActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: MainActivity): MainView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: MainPresenterImpl): MainPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: MainRouterImpl): MainRouter = router

}