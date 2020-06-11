package com.indra.coronaradar.features.exposition.presenter

import com.indra.coronaradar.common.extensions.format
import com.indra.coronaradar.datamanager.usecase.domain.DomainInfoUseCase
import com.indra.coronaradar.features.exposition.protocols.ExpositionPresenter
import com.indra.coronaradar.features.exposition.protocols.ExpositionRouter
import com.indra.coronaradar.features.exposition.protocols.ExpositionView
import com.indra.coronaradar.models.domain.ExposureInfo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExpositionPresenterImpl @Inject constructor(
    private val view: ExpositionView,
    private val router: ExpositionRouter,
    private val domainInfoUseCase: DomainInfoUseCase
) : ExpositionPresenter {

    override fun viewReady() {
        domainInfoUseCase.getExposureInfo()?.let {
            showExpositionInfo(it)
        }
    }

    override fun onResume() {
        domainInfoUseCase.getExposureInfo()?.let {
            setLastUpdateTime(it.lastUpdateTime)
        }
    }

    override fun onReportButtonClick() {
        router.navigateToCovidReport()
    }

    private fun showExpositionInfo(exposureInfo: ExposureInfo) {
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

}