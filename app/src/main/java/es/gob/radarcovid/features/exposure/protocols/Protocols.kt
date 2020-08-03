package es.gob.radarcovid.features.exposure.protocols

interface ExposureView {

    fun showExposureLevelLow()

    fun showExposureLevelHigh()

    fun showExposureLevelInfected()

    fun setUpdateAndExposureDates(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    )

    fun setInfectionDates(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    )

    fun setLastUpdateNoData()

    fun showDialerForSupport()

}

interface ExposurePresenter {

    fun viewReady()

    fun onResume()

    fun onPause()

    fun onContactButtonClick()

    fun onReportButtonClick()

    fun onMoreInfoButtonClick(url: String)

}

interface ExposureRouter {

    fun navigateToCovidReport()

    fun navigateToBrowser(url: String)

}