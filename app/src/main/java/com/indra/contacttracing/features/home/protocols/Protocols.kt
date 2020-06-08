package com.indra.contacttracing.features.home.protocols

interface HomeView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(daysElapsed: Int, hoursElapsed: Int, minutesElapsed: Int)

    fun setBluetoothBlockEnabled(enabled: Boolean)

}

interface HomePresenter {

    fun viewReady()

    fun onResume()

    fun onExpositionBlockClick()

    fun onReportButtonClick()

}

interface HomeRouter {

    fun navigateToExpositionDetail()

    fun navigateToCovidReport()

}