package com.indra.contacttracing.features.covidreport.form.router

import android.content.Context
import com.indra.contacttracing.features.covidreport.confirmation.Confirmation
import com.indra.contacttracing.features.covidreport.form.protocols.CovidReportRouter
import javax.inject.Inject

class CovidReportRouterImpl @Inject constructor(private val context: Context) : CovidReportRouter {

    override fun navigateToConfirmation() {
        Confirmation.open(context)
    }

}