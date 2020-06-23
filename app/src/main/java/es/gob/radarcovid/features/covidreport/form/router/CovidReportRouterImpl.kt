package es.gob.radarcovid.features.covidreport.form.router

import android.content.Context
import es.gob.radarcovid.features.covidreport.confirmation.Confirmation
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportRouter
import javax.inject.Inject

class CovidReportRouterImpl @Inject constructor(private val context: Context) : CovidReportRouter {

    override fun navigateToConfirmation() {
        Confirmation.open(context)
    }

}