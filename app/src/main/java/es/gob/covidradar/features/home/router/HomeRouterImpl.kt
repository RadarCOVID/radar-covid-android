package es.gob.covidradar.features.home.router

import android.content.Context
import es.gob.covidradar.features.covidreport.form.view.CovidReportActivity
import es.gob.covidradar.features.exposure.view.ExposureActivity
import es.gob.covidradar.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToExpositionDetail() = ExposureActivity.open(context)

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}