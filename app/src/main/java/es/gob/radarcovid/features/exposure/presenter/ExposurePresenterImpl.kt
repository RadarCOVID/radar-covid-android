/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.presenter

import com.squareup.otto.Subscribe
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.common.extensions.format
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
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
    private val exposureInfoUseCase: ExposureInfoUseCase
) : ExposurePresenter {

    override fun viewReady() {

    }

    override fun onResume() {
        BUS.register(this)
        val exposureInfo = exposureInfoUseCase.getExposureInfo()
        showExposureInfo(exposureInfo)
        showExposureHealedDialogIfRequired(exposureInfo.level)
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

    @Subscribe
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        val exposureInfo = exposureInfoUseCase.getExposureInfo()
        showExposureInfo(exposureInfo)
        showExposureHealedDialogIfRequired(exposureInfo.level)
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
                //FIRST EXECUTION, LOW RISK
                view.setLastUpdateNoData()
            }
            exposureInfo.level == ExposureInfo.Level.LOW -> {
                view.setExposureInfo(
                    exposureInfo.lastUpdateTime.format(),
                    null,
                    null,
                    null
                )
                view.hideExposureDates()
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
                view.hideExposureDates()
            }
            else -> { // HIGH EXPOSURE
                val millisElapsed = System.currentTimeMillis() - exposureInfo.lastExposureDate.time
                val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
                val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
                val minutesElapsed =
                    TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

                view.setExposureInfo(
                    exposureInfo.lastUpdateTime.format(),
                    daysElapsed.toInt(),
                    hoursElapsed.toInt(),
                    minutesElapsed.toInt()
                )
                view.hideExposureDates()
                //view.showExposureDates(exposureInfo.exposureDates.joinToString("\n") { it.format() })
            }
        }
    }

    private fun showExposureHealedDialogIfRequired(exposureLevel: ExposureInfo.Level) {
        if (exposureLevel == ExposureInfo.Level.LOW && exposureInfoUseCase.wasExposed()) {
            view.showExposureHealedDialog()
            exposureInfoUseCase.setExposed(false)
        }
    }

}