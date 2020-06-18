package es.gob.covidradar.features.covidreport.form.router

import android.content.Context
import es.gob.covidradar.features.covidreport.confirmation.Confirmation
import es.gob.covidradar.features.covidreport.form.protocols.CovidReportRouter
import javax.inject.Inject

class CovidReportRouterImpl @Inject constructor(private val context: Context) : CovidReportRouter {

    override fun navigateToConfirmation() {
        Confirmation.open(context)
    }

}