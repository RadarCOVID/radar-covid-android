package es.gob.radarcovid.features.main.di

import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.radarcovid.features.main.presenter.MainPresenterImpl
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.main.protocols.MainView
import es.gob.radarcovid.features.main.router.MainRouterImpl
import es.gob.radarcovid.features.main.view.MainActivity
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

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}