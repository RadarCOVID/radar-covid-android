package com.indra.coronaradar.features.exposure.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposure.protocols.ExpositionRouter
import javax.inject.Inject

class ExpositionRouterImpl @Inject constructor(private val context: Context) : ExpositionRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}