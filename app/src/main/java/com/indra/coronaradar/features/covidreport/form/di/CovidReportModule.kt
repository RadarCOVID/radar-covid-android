package com.indra.coronaradar.features.covidreport.form.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import com.indra.coronaradar.datamanager.repository.ContactTracingRepositoryImpl
import com.indra.coronaradar.features.covidreport.form.presenter.CovidReportPresenterImpl
import com.indra.coronaradar.features.covidreport.form.protocols.CovidReportPresenter
import com.indra.coronaradar.features.covidreport.form.protocols.CovidReportRouter
import com.indra.coronaradar.features.covidreport.form.protocols.CovidReportView
import com.indra.coronaradar.features.covidreport.form.router.CovidReportRouterImpl
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
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