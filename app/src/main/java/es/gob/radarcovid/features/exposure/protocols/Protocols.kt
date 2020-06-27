package es.gob.radarcovid.features.exposure.protocols

interface ExposureView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(date: String, daysElapsed: Int, hoursElapsed: Int, minutesElapsed: Int)

    fun setLastUpdateNoData()

    fun showDialerForSupport()

}

interface ExposurePresenter {

    fun viewReady()

    fun onResume()

    fun onPause()

    fun onContactButtonClick()

    fun onReportButtonClick()

    fun onMoreInfoButtonClick()

}

interface ExposureRouter {

    fun navigateToCovidReport()

    fun navigateToBrowser(url: String)

}