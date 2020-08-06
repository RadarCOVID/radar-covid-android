package es.gob.radarcovid.features.home.presenter

import com.squareup.otto.Subscribe
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.datamanager.usecase.ExposureRadarUseCase
import es.gob.radarcovid.datamanager.usecase.GetExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeRouter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.models.domain.ExposureInfo
import java.util.*
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val view: HomeView,
    private val router: HomeRouter,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val exposureRadarUseCase: ExposureRadarUseCase,
    private val getExposureInfoUseCase: GetExposureInfoUseCase
) : HomePresenter {


    override fun viewReady(activateRadar: Boolean) {
        if (onboardingCompletedUseCase.isOnBoardingCompleted()) {
            view.setRadarBlockChecked(exposureRadarUseCase.isRadarEnabled())
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
        updateViews()
    }

    override fun onPause() {
        BUS.unregister(this)
    }

    override fun onExposureBlockClick() {
        router.navigateToExpositionDetail()
    }

    override fun onExposureNotificationsDisabledWarningClick() {
        router.navigateToExposureNotificationSettings()
    }

    override fun onReportButtonClick() {
        if (!exposureRadarUseCase.isRadarEnabled())
            view.showUnableToReportCovidDialog()
        else if (getExposureInfoUseCase.getExposureInfo().level == ExposureInfo.Level.INFECTED)
            router.navigateToCovidReportConfirmation()
        else
            router.navigateToCovidReport()
    }

    override fun onSwitchRadarClick(currentlyEnabled: Boolean) {
        if (currentlyEnabled) {
            view.setRadarBlockChecked(false)
            exposureRadarUseCase.setRadarDisabled()
        } else {
            if (view.areBatteryOptimizationsIgnored())
                onBatteryOptimizationsIgnored()
            else
                view.requestIgnoreBatteryOptimizations()
        }
    }

    override fun onBatteryOptimizationsIgnored() {
        view.showLoading()
        exposureRadarUseCase.setRadarEnabled(
            onSuccess = {
                view.hideLoading()
                view.setRadarBlockChecked(true)
            },
            onError = {
                view.setRadarBlockChecked(false)
                view.hideLoadingWithError(it)
            },
            onCancelled = {
                view.setRadarBlockChecked(false)
                view.hideLoading()
            })
    }

    @Subscribe
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        updateViews()
    }

    private fun updateViews() {
        val exposureInfo = getExposureInfoUseCase.getExposureInfo()

        view.showBackgroundEnabled(
            exposureInfo.exposureNotificationsEnabled
                    && exposureRadarUseCase.isRadarEnabled()
        )

        if (exposureInfo.exposureNotificationsEnabled)
            view.hideWarningExposureNotificationsDisabled()
        else
            view.showWarningExposureNotificationsDisabled()

        if (exposureInfo.level == ExposureInfo.Level.INFECTED)
            view.hideReportButton()
        else
            view.showReportButton()

        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExposureLevelLow()
            ExposureInfo.Level.HIGH -> view.showExposureLevelHigh()
            ExposureInfo.Level.INFECTED -> view.showExposureLevelInfected()
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