package es.gob.covidradar.features.covidreport.form.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.datamanager.repository.ContactTracingRepository
import es.gob.covidradar.datamanager.repository.ContactTracingRepositoryImpl
import es.gob.covidradar.features.covidreport.form.presenter.CovidReportPresenterImpl
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportRouter
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportView
import es.gob.covidradar.features.covidreport.form.router.CovidReportRouterImpl
import es.gob.covidradar.features.covidreport.form.view.CovidReportActivity
import dagger.Module
import dagger.Provides

@Module
class CovidReportModule {

    @Provides
    fun providesContext(activity: CovidReportActivity): Context = activity

    @Provides
    fun providesActivity(activity: CovidReportActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: CovidReportActivity): CovidReportView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: CovidReportPresenterImpl): CovidReportPresenter = presenter

    @Provides
    @PerActivity
    fun providesCovidReportRouter(router: CovidReportRouterImpl): CovidReportRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}