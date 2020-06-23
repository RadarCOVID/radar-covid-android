package es.gob.radarcovid.features.home.protocols

import es.gob.radarcovid.common.view.RequestView

interface HomeView : RequestView {

    fun showInitializationCheckAnimation()

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