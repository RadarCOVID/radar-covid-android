package com.indra.coronaradar.features.home.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposition.view.ExpositionActivity
import com.indra.coronaradar.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToExpositionDetail() = ExpositionActivity.open(context)

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}