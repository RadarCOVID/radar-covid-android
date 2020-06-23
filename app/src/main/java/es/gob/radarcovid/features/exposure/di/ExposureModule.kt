package es.gob.radarcovid.features.exposure.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.radarcovid.features.exposure.presenter.ExposurePresenterImpl
import es.gob.radarcovid.features.exposure.protocols.ExposurePresenter
import es.gob.radarcovid.features.exposure.protocols.ExposureRouter
import es.gob.radarcovid.features.exposure.protocols.ExposureView
import es.gob.radarcovid.features.exposure.router.ExposureRouterImpl
import es.gob.radarcovid.features.exposure.view.ExposureActivity
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