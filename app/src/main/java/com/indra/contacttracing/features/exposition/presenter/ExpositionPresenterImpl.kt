package com.indra.contacttracing.features.exposition.presenter

import com.indra.contacttracing.features.exposition.protocols.ExpositionPresenter
import com.indra.contacttracing.features.exposition.protocols.ExpositionRouter
import com.indra.contacttracing.features.exposition.protocols.ExpositionView
import javax.inject.Inject

class ExpositionPresenterImpl @Inject constructor(
    private val view: ExpositionView,
    private val router: ExpositionRouter
) : ExpositionPresenter {

    override fun viewReady() {
        view.showExpositionLevelHigh()
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

}