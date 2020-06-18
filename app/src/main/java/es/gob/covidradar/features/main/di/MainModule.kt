package es.gob.covidradar.features.main.di

import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.covidradar.features.main.presenter.MainPresenterImpl
import es.gob.covidradar.features.main.protocols.MainPresenter
import es.gob.covidradar.features.main.protocols.MainRouter
import es.gob.covidradar.features.main.protocols.MainView
import es.gob.covidradar.features.main.router.MainRouterImpl
import es.gob.covidradar.features.main.view.MainActivity
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