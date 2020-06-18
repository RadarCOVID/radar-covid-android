package es.gob.covidradar.features.home.di

import android.app.Activity
import android.content.Context
import es.gob.covidradar.common.di.scope.PerFragment
import es.gob.covidradar.features.home.presenter.HomePresenterImpl
import es.gob.covidradar.features.home.protocols.HomePresenter
import es.gob.covidradar.features.home.protocols.HomeRouter
import es.gob.covidradar.features.home.protocols.HomeView
import es.gob.covidradar.features.home.router.HomeRouterImpl
import es.gob.covidradar.features.home.view.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun providesContext(fragment: HomeFragment): Context = fragment.context!!

    @Provides
    fun providesActivity(fragment: HomeFragment): Activity = fragment.activity!!

    @Provides
    fun providesView(fragment: HomeFragment): HomeView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: HomePresenterImpl): HomePresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: HomeRouterImpl): HomeRouter = router

}