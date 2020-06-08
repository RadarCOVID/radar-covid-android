package com.indra.contacttracing.features.exposition.protocols

interface ExpositionView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(date: String, daysElapsed: Int, hoursElapsed: Int, minutesElapsed: Int)

}

interface ExpositionPresenter {

    fun viewReady()

    fun onResume()

    fun onReportButtonClick()

}

interface ExpositionRouter {

    fun navigateToCovidReport()

}