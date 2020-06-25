package es.gob.radarcovid.features.exposure.protocols

interface ExposureView {

    fun showExpositionLevelLow()

    fun showExpositionLevelMedium()

    fun showExpositionLevelHigh()

    fun setLastUpdateTime(date: String, daysElapsed: Int, hoursElapsed: Int, minutesElapsed: Int)

    fun showDialerForSupport()

}

interface ExposurePresenter {

    fun viewReady()

    fun onResume()

    fun onContactButtonClick()

    fun onReportButtonClick()

}

interface ExposureRouter {

    fun navigateToCovidReport()

}