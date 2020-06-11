package com.indra.coronaradar.features.covidreport.form.router

import android.content.Context
import com.indra.coronaradar.features.covidreport.confirmation.Confirmation
import com.indra.coronaradar.features.covidreport.form.protocols.CovidReportRouter
import javax.inject.Inject

class CovidReportRouterImpl @Inject constructor(private val context: Context) : CovidReportRouter {

    override fun navigateToConfirmation() {
        Confirmation.open(context)
    }

}