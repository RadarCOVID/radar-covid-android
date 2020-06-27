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
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    override fun onResume() {
        BUS.register(this)
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
            ExposureInfo.Level.LOW -> view.showExpositionLevelLow()
            ExposureInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExposureInfo.Level.HIGH -> view.showExpositionLevelHigh()
        }

        setUpdateAndExposureDates(
            exposureInfo.level,
            exposureInfo.lastUpdateTime,
            exposureInfo.lastExposureDate
        )
    }

    private fun setUpdateAndExposureDates(
        exposureLevel: ExposureInfo.Level,
        lastUpdateTime: Date,
        lastExposureDate: Date
    ) {
        when {
            lastUpdateTime == Date(0) -> {
                view.setLastUpdateNoData()
            }
            exposureLevel == ExposureInfo.Level.LOW -> {
                view.setUpdateAndExposureDates(lastUpdateTime.format(), null, null, null)
            }
            else -> {
                val millisElapsed = System.currentTimeMillis() - lastExposureDate.time
                val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
                val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
                val minutesElapsed =
                    TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

                view.setUpdateAndExposureDates(
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