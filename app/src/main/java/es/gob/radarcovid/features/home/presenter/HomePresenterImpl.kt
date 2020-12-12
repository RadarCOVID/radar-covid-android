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
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val view: HomeView,
    private val router: HomeRouter,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val exposureRadarUseCase: ExposureRadarUseCase,
    private val exposureInfoUseCase: ExposureInfoUseCase,
    private val fakeExposureInfoUseCase: FakeExposureInfoUseCase,
    private val legalTermsUseCase: LegalTermsUseCase
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
        updateViews(exposureInfoUseCase.getExposureInfo())
    }

    override fun onPause() {
        BUS.unregister(this)
    }

    override fun onExposureBlockClick() {
        router.navigateToExpositionDetail()
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

    override fun needChangeLegalTerms(): Boolean {
        val settingLegalTermsVersionCode = legalTermsUseCase.getSettingsLegalTermsVersionCode()
        val savedLegalTermsVersionCode = legalTermsUseCase.getSavedLegalTermsVersionCode()
        return settingLegalTermsVersionCode != savedLegalTermsVersionCode;
    }

    override fun legalTermsAccepted() {
        legalTermsUseCase.updateLegalTermsVersionCode()
        whenViewReady(activateRadar)
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
    }

    @Subscribe
    fun onExposureStatusChange(event: EventExposureStatusChange) {
        updateViews(exposureInfoUseCase.getExposureInfo())
    }

    private fun showExposureHealedDialogIfRequired(exposureLevel: ExposureInfo.Level) {
        if (exposureLevel == ExposureInfo.Level.LOW && exposureInfoUseCase.wasExposed()) {
            view.showExposureHealedDialog()
            exposureInfoUseCase.setExposed(false)
        }
    }

    private fun updateViews(exposureInfo: ExposureInfo) {
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

        when (exposureInfo.level) {
            ExposureInfo.Level.LOW -> view.showExposureBlockLow()
            ExposureInfo.Level.HIGH -> view.showExposureBlockHigh()
            ExposureInfo.Level.INFECTED -> view.showExposureBlockInfected()
        }

        showExposureHealedDialogIfRequired(exposureInfo.level)
    }

}