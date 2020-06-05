package com.indra.contacttracing.features.covidreport.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.common.base.BaseBackNavigationActivity
import com.indra.contacttracing.features.covidreport.protocols.CovidReportPresenter
import com.indra.contacttracing.features.covidreport.protocols.CovidReportView
import javax.inject.Inject

class CovidReportActivity : BaseBackNavigationActivity(), CovidReportView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, CovidReportActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: CovidReportPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_report)

        presenter.viewReady()
    }

}