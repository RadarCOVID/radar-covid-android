package com.indra.contacttracing.features.home.presenter

import android.util.Log
import com.indra.contacttracing.datamanager.usecase.domain.ExpositionInfoUseCase
import com.indra.contacttracing.features.home.protocols.HomePresenter
import com.indra.contacttracing.features.home.protocols.HomeRouter
import com.indra.contacttracing.features.home.protocols.HomeView
import com.indra.contacttracing.models.domain.ExpositionInfo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val view: HomeView,
    private val router: HomeRouter,
    private val expositionInfoUseCase: ExpositionInfoUseCase
) : HomePresenter {

    private var expositionInfo: ExpositionInfo? = null

    override fun viewReady() {
        expositionInfo = getMockExpositionInfo()

        expositionInfo?.let {
            showExpositionInfo(it)
        }
    }

    override fun onResume() {
//        expositionInfo?.let {
//            setLastUpdateTime(it.lastUpdateTime)
//        }
    }

    override fun onExpositionBlockClick() {
        expositionInfo?.let {
            expositionInfoUseCase.setExpositionInfo(it)
            router.navigateToExpositionDetail()
        }
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    override fun onSwitchRadarStatusChange(enabled: Boolean) {
        Log.d("test", "radar status changed $enabled")
    }

    private fun showExpositionInfo(expositionInfo: ExpositionInfo) {
        when (expositionInfo.level) {
            ExpositionInfo.Level.LOW -> view.showExpositionLevelLow()
            ExpositionInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExpositionInfo.Level.HIGH -> view.showExpositionLevelHigh()
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

    private fun getMockExpositionInfo(): ExpositionInfo {
        val res = ExpositionInfo()

        res.lastUpdateTime = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DAY_OF_YEAR, -2)

            add(Calendar.HOUR, -1)

            add(Calendar.MINUTE, -12)
        }.time

        res.level = ExpositionInfo.Level.MEDIUM

        return res
    }

}