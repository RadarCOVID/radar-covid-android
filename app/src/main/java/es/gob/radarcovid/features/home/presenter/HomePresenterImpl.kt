package es.gob.radarcovid.features.home.presenter

import com.squareup.otto.Subscribe
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.datamanager.usecase.EnableExposureRadarUseCase
import es.gob.radarcovid.datamanager.usecase.GetExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeRouter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.models.domain.ExposureInfo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val view: HomeView,
    private val router: HomeRouter,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val enableExposureRadarUseCase: EnableExposureRadarUseCase,
    private val getExposureInfoUseCase: GetExposureInfoUseCase
) : HomePresenter {


    override fun viewReady(activateRadar: Boolean) {
        if (onboardingCompletedUseCase.isOnBoardingCompleted()) {
            view.setRadarBlockChecked(enableExposureRadarUseCase.isRadarEnabled())
        } else {
            onboardingCompletedUseCase.setOnboardingCompleted(true)
            if (activateRadar) {
                onSwitchRadarClick(false)
                view.showInitializationCheckAnimation()
            }
        }
    }

    override fun onResume() {
        BUS.register(this)
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    override fun onPause() {
        BUS.unregister(this)
    }

    override fun onExpositionBlockClick() {
        if (getExposureInfoUseCase.getExposureInfo().level == ExposureInfo.Level.INFECTED)
            router.navigateToCovidReportConfirmation()
        else
            router.navigateToExpositionDetail()
    }

    override fun onReportButtonClick() {
        if (!enableExposureRadarUseCase.isRadarEnabled())
            view.showUnableToReportCovidDialog()
        else if (getExposureInfoUseCase.getExposureInfo().level == ExposureInfo.Level.INFECTED)
            router.navigateToCovidReportConfirmation()
        else
            router.navigateToCovidReport()
    }

    override fun onSwitchRadarClick(currentlyEnabled: Boolean) {
        if (currentlyEnabled) {
            view.setRadarBlockChecked(false)
            enableExposureRadarUseCase.setRadarDisabled()
        } else {
            if (view.areBatteryOptimizationsIgnored())
                onBatteryOptimizationsIgnored()
            else
                view.requestIgnoreBatteryOptimizations()
        }
    }

    override fun onBatteryOptimizationsIgnored() {
        view.showLoading()
        enableExposureRadarUseCase.setRadarEnabled(
            onSuccess = {
                view.hideLoading()
                view.setRadarBlockChecked(true)
            },
            onError = {
                view.setRadarBlockChecked(false)
                view.hideLoading()
                view.showError(it)
            },
            onCancelled = {
                view.setRadarBlockChecked(false)
                view.hideLoading()
            })
    }

    /* THIS METHOD IS CALLED ON RESUME BECAUSE THE MANUAL DP3T SYNC METHOD IN MainPresenterImpl IS
    SENDING THE BROADCAST WHEN onResume IS EXECUTED */
    @Subscribe
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    private fun showExposureInfo(exposureInfo: ExposureInfo) {
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExpositionLevelLow()
            ExposureInfo.Level.HIGH -> view.showExpositionLevelHigh()
            ExposureInfo.Level.INFECTED -> view.showExpositionLevelInfected()
        }

        //setLastUpdateTime(expositionInfo.lastUpdateTime)
    }

    private fun setLastUpdateTime(lastUpdateTime: Date) {
        val millisElapsed = System.currentTimeMillis() - lastUpdateTime.time
        val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
        val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
        val minutesElapsed =
            TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

        view.setLastUpdateTime(daysElapsed.toInt(), hoursElapsed.toInt(), minutesElapsed.toInt())
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