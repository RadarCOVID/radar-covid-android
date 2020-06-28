package es.gob.radarcovid.features.covidreport.form.router

import android.content.Context
import es.gob.radarcovid.features.covidreport.confirmation.ConfirmationActivity
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import javax.inject.Inject

class CovidReportRouterImpl @Inject constructor(private val context: Context) : CovidReportRouter {

    override fun navigateToConfirmation() {
        ConfirmationActivity.open(context)
    }

}