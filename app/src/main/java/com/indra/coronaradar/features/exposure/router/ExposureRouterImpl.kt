package com.indra.coronaradar.features.exposure.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.form.view.CovidReportActivity
import com.indra.coronaradar.features.exposure.protocols.ExposureRouter
import javax.inject.Inject

class ExposureRouterImpl @Inject constructor(private val context: Context) : ExposureRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}