package com.indra.coronaradar.features.home.presenter

import com.indra.coronaradar.datamanager.usecase.EnableExposureRadarUseCase
import com.indra.coronaradar.datamanager.usecase.GetExposureInfoUseCase
import com.indra.coronaradar.datamanager.usecase.OnboardingCompletedUseCase
import com.indra.coronaradar.features.home.protocols.HomePresenter
import com.indra.coronaradar.features.home.protocols.HomeRouter
import com.indra.coronaradar.features.home.protocols.HomeView
import com.indra.coronaradar.models.domain.ExposureInfo
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


    override fun viewReady() {

        if (onboardingCompletedUseCase.isOnBoardingCompleted()) {
            view.setRadarBlockChecked(enableExposureRadarUseCase.isRadarEnabled())
        } else {
            onboardingCompletedUseCase.setOnboardingCompleted(true)
            onSwitchRadarClick(false)
        }
    }

    override fun onResume() {
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
    }

    override fun onExpositionBlockClick() {
        showExposureInfo(getExposureInfoUseCase.getExposureInfo())
        router.navigateToExpositionDetail()
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    override fun onSwitchRadarClick(currentlyEnabled: Boolean) {
        if (currentlyEnabled) {
            view.setRadarBlockChecked(false)
            enableExposureRadarUseCase.setRadarDisabled()
        } else {
            view.showLoading()
//            Handler().postDelayed({
//                view.setRadarBlockChecked(true)
//                view.hideLoading()
//            }, 2000)
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
    }

    private fun showExposureInfo(exposureInfo: ExposureInfo) {
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExpositionLevelLow()
            ExposureInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExposureInfo.Level.HIGH -> view.showExpositionLevelHigh()
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

        res.level = ExposureInfo.Level.MEDIUM

        return res
    }

}