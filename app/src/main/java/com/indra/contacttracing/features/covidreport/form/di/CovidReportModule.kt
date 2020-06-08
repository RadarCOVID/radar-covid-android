package com.indra.contacttracing.features.covidreport.form.di

import android.content.Context
import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.covidreport.form.presenter.CovidReportPresenterImpl
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportPresenter
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportRouter
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportView
import com.indra.contacttracing.features.covidreport.form.router.CovidReportRouterImpl
import com.indra.contacttracing.features.covidreport.form.view.CovidReportActivity
import dagger.Module
import dagger.Provides

@Module
class CovidReportModule {

    @Provides
    fun providesContext(activity: CovidReportActivity): Context = activity

    @Provides
    fun providesView(activity: CovidReportActivity): CovidReportView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: CovidReportPresenterImpl): CovidReportPresenter = presenter

    @Provides
    @PerActivity
    fun providesCovidReportRouter(router: CovidReportRouterImpl): CovidReportRouter = router

}