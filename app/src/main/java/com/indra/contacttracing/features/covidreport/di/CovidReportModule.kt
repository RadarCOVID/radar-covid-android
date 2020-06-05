package com.indra.contacttracing.features.covidreport.di

import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.covidreport.presenter.CovidReportPresenterImpl
import com.indra.contacttracing.features.covidreport.protocols.CovidReportPresenter
import com.indra.contacttracing.features.covidreport.protocols.CovidReportView
import com.indra.contacttracing.features.covidreport.view.CovidReportActivity
import dagger.Module
import dagger.Provides

@Module
class CovidReportModule {

    @Provides
    fun providesView(activity: CovidReportActivity): CovidReportView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: CovidReportPresenterImpl): CovidReportPresenter = presenter

}