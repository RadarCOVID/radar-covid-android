package es.gob.radarcovid.features.home.router

import android.content.Context
import es.gob.radarcovid.features.covidreport.confirmation.ConfirmationActivity
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.view.ExposureActivity
import es.gob.radarcovid.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToExpositionDetail() = ExposureActivity.open(context)

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

    override fun navigateToCovidReportConfirmation() = ConfirmationActivity.open(context)

}