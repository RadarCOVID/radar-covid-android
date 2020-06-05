package com.indra.contacttracing.features.home.router

import android.content.Context
import com.indra.contacttracing.features.home.protocols.HomeRouter
import com.indra.contacttracing.features.covidreport.view.CovidReportActivity
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

}