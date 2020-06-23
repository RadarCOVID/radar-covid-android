package es.gob.radarcovid.features.splash.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.radarcovid.features.splash.presenter.SplashPresenterImpl
import es.gob.radarcovid.features.splash.protocols.SplashPresenter
import es.gob.radarcovid.features.splash.protocols.SplashRouter
import es.gob.radarcovid.features.splash.protocols.SplashView
import es.gob.radarcovid.features.splash.router.SplashRouterImpl
import es.gob.radarcovid.features.splash.view.SplashActivity
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