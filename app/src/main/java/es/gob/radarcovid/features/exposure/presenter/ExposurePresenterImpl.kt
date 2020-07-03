package es.gob.radarcovid.features.exposure.presenter

import com.squareup.otto.Subscribe
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
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
        BUS.register(this)
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    override fun onPause() {
        BUS.unregister(this)
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

    @Subscribe
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    private fun showExposureInfo(exposureInfo: ExposureInfo) {
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExposureLevelLow()
            ExposureInfo.Level.HIGH -> view.showExposureLevelHigh()
            ExposureInfo.Level.INFECTED -> view.showExposureLevelInfected()
        }

        setUpdateAndExposureDates(exposureInfo)
    }

    private fun setUpdateAndExposureDates(exposureInfo: ExposureInfo) {
        when {
            exposureInfo.lastUpdateTime == Date(0) -> {
                view.setLastUpdateNoData()
            }
            exposureInfo.level == ExposureInfo.Level.LOW -> {
                view.setUpdateAndExposureDates(
                    exposureInfo.lastUpdateTime.format(),
                    null,
                    null,
                    null
                )
            }
            exposureInfo.level == ExposureInfo.Level.INFECTED -> {
                val millisElapsed = System.currentTimeMillis() - exposureInfo.lastExposureDate.time
                val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
                val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
                val minutesElapsed =
                    TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

                view.setInfectionDates(
                    exposureInfo.lastUpdateTime.format(),
                    daysElapsed.toInt(),
                    hoursElapsed.toInt(),
                    minutesElapsed.toInt()
                )
            }
            else -> {
                val millisElapsed = System.currentTimeMillis() - exposureInfo.lastExposureDate.time
                val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
                val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
                val minutesElapsed =
                    TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

                view.setUpdateAndExposureDates(
                    exposureInfo.lastUpdateTime.format(),
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