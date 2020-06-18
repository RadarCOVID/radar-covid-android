package es.gob.covidradar.features.exposure.presenter

import es.gob.covidradar.common.extensions.format
import es.gob.covidradar.datamanager.usecase.GetExposureInfoUseCase
import es.gob.covidradar.features.exposure.protocols.ExposurePresenter
import es.gob.covidradar.features.exposure.protocols.ExposureRouter
import es.gob.covidradar.features.exposure.protocols.ExposureView
import es.gob.covidradar.models.domain.ExposureInfo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExposurePresenterImpl @Inject constructor(
    private val view: ExposureView,
    private val router: ExposureRouter,
    private val getExposureInfoUseCase: GetExposureInfoUseCase
) : ExposurePresenter {

    override fun viewReady() {

    }

    override fun onResume() {
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    private fun showExposureInfo(exposureInfo: ExposureInfo) {
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExpositionLevelLow()
            ExposureInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExposureInfo.Level.HIGH -> view.showExpositionLevelHigh()
        }

        setLastUpdateTime(exposureInfo.lastUpdateTime)
    }

    private fun setLastUpdateTime(lastUpdateTime: Date) {
        val millisElapsed = System.currentTimeMillis() - lastUpdateTime.time
        val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
        val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
        val minutesElapsed =
            TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

        view.setLastUpdateTime(
            lastUpdateTime.format(),
            daysElapsed.toInt(),
            hoursElapsed.toInt(),
            minutesElapsed.toInt()
        )
    }

    private fun getMockExposureInfo(): ExposureInfo {
        val res = ExposureInfo()

        res.lastUpdateTime = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DAY_OF_YEAR, -2)

            add(Calendar.HOUR, -1)

            add(Calendar.MINUTE, -12)
        }.time

        res.level = ExposureInfo.Level.HIGH

        return res
    }

}