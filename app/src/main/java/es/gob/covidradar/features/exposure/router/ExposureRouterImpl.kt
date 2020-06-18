package es.gob.covidradar.features.exposure.router

import android.content.Context
import es.gob.covidradar.features.covidreport.form.view.CovidReportActivity
import es.gob.covidradar.features.exposure.protocols.ExposureRouter
import javax.inject.Inject

class ExposureRouterImpl @Inject constructor(private val context: Context) : ExposureRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}