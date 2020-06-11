package com.indra.coronaradar.features.exposition.presenter

import com.indra.coronaradar.common.extensions.format
import com.indra.coronaradar.datamanager.usecase.domain.ExpositionInfoUseCase
import com.indra.coronaradar.features.exposition.protocols.ExpositionPresenter
import com.indra.coronaradar.features.exposition.protocols.ExpositionRouter
import com.indra.coronaradar.features.exposition.protocols.ExpositionView
import com.indra.coronaradar.models.domain.ExpositionInfo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExpositionPresenterImpl @Inject constructor(
    private val view: ExpositionView,
    private val router: ExpositionRouter,
    private val expositionInfoUseCase: ExpositionInfoUseCase
) : ExpositionPresenter {

    override fun viewReady() {
        expositionInfoUseCase.getExpositionInfo()?.let {
            showExpositionInfo(it)
        }
    }

    override fun onResume() {
        expositionInfoUseCase.getExpositionInfo()?.let {
            setLastUpdateTime(it.lastUpdateTime)
        }
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    private fun showExpositionInfo(expositionInfo: ExpositionInfo) {
        when (expositionInfo.level) {
            ExpositionInfo.Level.LOW -> view.showExpositionLevelLow()
            ExpositionInfo.Level.MEDIUM -> view.showExpositionLevelMedium()
            ExpositionInfo.Level.HIGH -> view.showExpositionLevelHigh()
        }

        setLastUpdateTime(expositionInfo.lastUpdateTime)

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

}