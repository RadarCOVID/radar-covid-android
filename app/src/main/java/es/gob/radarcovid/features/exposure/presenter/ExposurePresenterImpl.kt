package es.gob.radarcovid.features.exposure.presenter

import es.gob.radarcovid.common.extensions.format
import es.gob.radarcovid.datamanager.usecase.GetExposureInfoUseCase
import es.gob.radarcovid.features.exposure.protocols.ExposurePresenter
import es.gob.radarcovid.features.exposure.protocols.ExposureRouter
import es.gob.radarcovid.features.exposure.protocols.ExposureView
import es.gob.radarcovid.models.domain.ExposureInfo
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

    override fun onContactButtonClick() {
        view.showDialerForSupport()
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    override fun onMoreInfoButtonClick() {
        router.navigateToBrowser("https://www.mscbs.gob.es/profesionales/saludPublica/ccayes/alertasActual/nCov-China/ciudadania.htm")
    }

    private fun showExposureInfo(exposureInfo: ExposureInfo) {
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExpositionLevelLow()
            ExposureInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExposureInfo.Level.HIGH -> view.showExpositionLevelHigh()
        }

        setLastUpdateTime(exposureInfo.level, exposureInfo.lastUpdateTime)
    }

    private fun setLastUpdateTime(exposureLevel: ExposureInfo.Level, lastUpdateTime: Date) {
        when {
            lastUpdateTime == Date(0) -> {
                view.setLastUpdateNoData()
            }
            exposureLevel == ExposureInfo.Level.LOW -> {
                view.setLastUpdateTime(
                    lastUpdateTime.format(),
                    0,
                    0,
                    0
                )
            }
            else -> {
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
        }

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