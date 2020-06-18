package es.gob.covidradar.features.splash.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.covidradar.features.splash.presenter.SplashPresenterImpl
import es.gob.covidradar.features.splash.protocols.SplashPresenter
import es.gob.covidradar.features.splash.protocols.SplashRouter
import es.gob.covidradar.features.splash.protocols.SplashView
import es.gob.covidradar.features.splash.router.SplashRouterImpl
import es.gob.covidradar.features.splash.view.SplashActivity
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun providesContext(activity: SplashActivity): Context = activity

    @Provides
    fun providesActivity(activity: SplashActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: SplashActivity): SplashView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: SplashPresenterImpl): SplashPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: SplashRouterImpl): SplashRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}