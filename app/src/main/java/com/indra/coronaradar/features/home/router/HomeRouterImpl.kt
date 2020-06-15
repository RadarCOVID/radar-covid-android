package com.indra.coronaradar.features.home.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposure.view.ExposureActivity
import com.indra.coronaradar.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToExpositionDetail() = ExposureActivity.open(context)

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}