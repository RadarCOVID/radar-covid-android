package com.indra.contacttracing.features.main.di

import androidx.appcompat.app.AppCompatActivity
import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.main.presenter.MainPresenterImpl
import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainRouter
import com.indra.contacttracing.features.main.protocols.MainView
import com.indra.contacttracing.features.main.router.MainRouterImpl
import com.indra.contacttracing.features.main.view.MainActivity
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