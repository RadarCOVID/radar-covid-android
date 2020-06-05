package com.indra.contacttracing.features.exposition.protocols

interface ExpositionView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(text: String)

}

interface ExpositionPresenter {

    fun viewReady()

    fun onReportButtonClick()

}

interface ExpositionRouter {

    fun navigateToCovidReport()

}