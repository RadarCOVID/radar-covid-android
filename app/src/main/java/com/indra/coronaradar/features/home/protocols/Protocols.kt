package com.indra.coronaradar.features.home.protocols

import com.indra.coronaradar.common.view.RequestView

interface HomeView : RequestView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(daysElapsed: Int, hoursElapsed: Int, minutesElapsed: Int)

    fun setRadarBlockChecked(isChecked: Boolean)

}

interface HomePresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onExpositionBlockClick()

    fun onReportButtonClick()

    fun onSwitchRadarClick(currentlyEnabled: Boolean)

}

interface HomeRouter {

    fun navigateToExpositionDetail()

    fun navigateToCovidReport()

}