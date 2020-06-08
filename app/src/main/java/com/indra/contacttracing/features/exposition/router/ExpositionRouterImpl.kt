package com.indra.contacttracing.features.exposition.router

import android.content.Context
import com.indra.contacttracing.features.covidreport.form.view.CovidReportActivity
import com.indra.contacttracing.features.exposition.protocols.ExpositionRouter
import javax.inject.Inject

class ExpositionRouterImpl @Inject constructor(private val context: Context) : ExpositionRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}