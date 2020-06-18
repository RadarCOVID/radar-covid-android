package es.gob.covidradar.features.exposure.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.covidradar.features.exposure.presenter.ExposurePresenterImpl
import es.gob.covidradar.features.exposure.protocols.ExposurePresenter
import es.gob.covidradar.features.exposure.protocols.ExposureRouter
import es.gob.covidradar.features.exposure.protocols.ExposureView
import es.gob.covidradar.features.exposure.router.ExposureRouterImpl
import es.gob.covidradar.features.exposure.view.ExposureActivity
import dagger.Module
import dagger.Provides

@Module
class ExposureModule {

    @Provides
    fun providesContext(activity: ExposureActivity): Context = activity

    @Provides
    fun providesActivity(activity: ExposureActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: ExposureActivity): ExposureView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: ExposurePresenterImpl): ExposurePresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: ExposureRouterImpl): ExposureRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}