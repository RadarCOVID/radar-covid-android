/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.home.presenter

import com.squareup.otto.Subscribe
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.datamanager.usecase.*
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeRouter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.models.domain.Environment
import es.gob.radarcovid.models.domain.ExposureInfo
import es.gob.radarcovid.models.domain.VenueRecord
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val view: HomeView,
    private val router: HomeRouter,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val exposureRadarUseCase: ExposureRadarUseCase,
    private val exposureInfoUseCase: ExposureInfoUseCase,
    private val fakeExposureInfoUseCase: FakeExposureInfoUseCase,
    private val legalTermsUseCase: LegalTermsUseCase,
    private val getHealingTimeUseCase: GetHealingTimeUseCase,
    private val venueMatcherUseCase: VenueMatcherUseCase
) : HomePresenter {

    private var activateRadar: Boolean = false

    override fun viewReady(activateRadar: Boolean) {

        this.activateRadar = activateRadar

        if (needChangeLegalTerms()) {
            view.showUpdateLegalTermsDialog()
        } else {
            whenViewReady(activateRadar)
        }
    }

    override fun onResume() {
        BUS.register(this)
        updateViews(
            exposureInfoUseCase.getExposureInfo(),
            venueMatcherUseCase.getVenueExposureInfo()
        )
    }

    override fun onPause() {
        BUS.unregister(this)
    }

    override fun onExposureBlockClick() {
        router.navigateToExpositionDetail(false)
    }

    override fun onVenueExposureBlockClick() {
        router.navigateToExpositionDetail(true)
    }

    override fun onReportButtonClick() {
        if (!exposureRadarUseCase.isRadarEnabled())
            view.showUnableToReportCovidDialog()
        else
            router.navigateToCovidReport()
    }

    override fun onFakeExposureButtonClick() {
        if (fakeExposureInfoUseCase.getEnvironment() == Environment.PRE)
            fakeExposureInfoUseCase.addFakeExposureDay()
    }

    override fun onFakeShowExposureHealedDialogClick() {
        if (fakeExposureInfoUseCase.getEnvironment() == Environment.PRE)
            view.showExposureHealedDialog()
    }

    override fun onSwitchRadarClick(currentlyEnabled: Boolean) {
        if (currentlyEnabled) {
            view.setRadarBlockChecked(false)
            exposureRadarUseCase.setRadarDisabled()
            view.updateContentDescriptionRadar(false)
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
                view.updateContentDescriptionRadar(true)
            },
            onError = {
                view.setRadarBlockChecked(false)
                view.hideLoadingWithCommonError()
                view.updateContentDescriptionRadar(false)
            },
            onCancelled = {
                view.setRadarBlockChecked(false)
                view.hideLoading()
                view.updateContentDescriptionRadar(false)
            })
    }

    override fun needChangeLegalTerms(): Boolean {
        val settingLegalTermsVersionCode = legalTermsUseCase.getSettingsLegalTermsVersionCode()
        val savedLegalTermsVersionCode = legalTermsUseCase.getSavedLegalTermsVersionCode()
        return settingLegalTermsVersionCode != savedLegalTermsVersionCode
    }

    override fun legalTermsAccepted() {
        legalTermsUseCase.updateLegalTermsVersionCode()
        whenViewReady(activateRadar)
    }

    override fun onButtonShareClick() {
        view.showShareDialog()
    }

    override fun doShareApp(message: String) {
        router.shareApp(message)
    }

    private fun whenViewReady(activateRadar: Boolean) {

        if (fakeExposureInfoUseCase.getEnvironment() == Environment.PRE)
            view.setFakeExposureButton()

        if (!onboardingCompletedUseCase.isOnBoardingCompleted()) {
            onboardingCompletedUseCase.setOnboardingCompleted(true)
            view.showInitializationCheckAnimation()
        }

        if (activateRadar && !exposureRadarUseCase.isRadarEnabled())
            onSwitchRadarClick(false)
        else
            view.updateContentDescriptionRadar(exposureRadarUseCase.isRadarEnabled())
    }

    @Subscribe()
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        updateViews(
            exposureInfoUseCase.getExposureInfo(),
            venueMatcherUseCase.getVenueExposureInfo()
        )
    }

    private fun showExposureHealedDialogIfRequired(
        exposureLevel: ExposureInfo.Level,
        daysLeft: Int
    ) {
        if (exposureLevel == ExposureInfo.Level.LOW && exposureInfoUseCase.wasExposed()) {
            view.showExposureHealedDialog()
            exposureInfoUseCase.setExposed(false)
        } else if (exposureLevel == ExposureInfo.Level.HIGH && daysLeft < 0) {
            view.showExposureHealedDialog()
            exposureInfoUseCase.resetExposure()
            exposureInfoUseCase.setExposed(false)
        }
    }

    private fun updateViews(exposureInfo: ExposureInfo, venueExposureInfo: VenueRecord?) {
        if (!exposureInfo.exposureNotificationsEnabled && exposureRadarUseCase.isRadarEnabled())
            exposureRadarUseCase.setRadarDisabled()

        if (exposureInfo.level == ExposureInfo.Level.INFECTED) {
            view.showBackgroundEnabled(true)
            view.setRadarBlockEnabled(false)
            view.hideReportButton()
        } else {
            view.showBackgroundEnabled(exposureRadarUseCase.isRadarEnabled())
            view.setRadarBlockEnabled(true)
            view.setRadarBlockChecked(exposureRadarUseCase.isRadarEnabled())
            view.showReportButton()
        }

        var daysLeftVenueExposure = 0L
        if (venueExposureInfo != null) {
            val millisElapsed = System.currentTimeMillis() - venueExposureInfo.dateOut!!.time
            val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
            val daysToHeal =
                getHealingTimeUseCase.getHealingTime().exposureHighMinutes / 60 / 24
            daysLeftVenueExposure = daysToHeal - daysElapsed
        }

        var daysLeft = 0L
        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> {
                if (venueExposureInfo != null) {
                    view.showVenueExposureBlock(daysLeftVenueExposure.toInt(), true)
                } else {
                    view.showExposureBlockLow()
                }
            }
            ExposureInfo.Level.HIGH -> {
                val millisElapsed = System.currentTimeMillis() - exposureInfo.lastExposureDate.time
                val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
                val daysToHeal =
                    getHealingTimeUseCase.getHealingTime().exposureHighMinutes / 60 / 24
                daysLeft = daysToHeal - daysElapsed

                view.showExposureBlockHigh(daysLeft.toInt(), venueExposureInfo == null)
                if (venueExposureInfo != null) {
                    view.showVenueExposureBlock(daysLeftVenueExposure.toInt(), false)
                }
            }
            ExposureInfo.Level.INFECTED -> view.showExposureBlockInfected()
        }

        showExposureHealedDialogIfRequired(exposureInfo.level, daysLeft.toInt())
    }

}