package com.indra.coronaradar.features.exposition.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposition.protocols.ExpositionRouter
import javax.inject.Inject

class ExpositionRouterImpl @Inject constructor(private val context: Context) : ExpositionRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}