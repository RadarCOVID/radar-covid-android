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

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.gob.radarcovid.datamanager.usecase.*
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeRouter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.models.domain.Environment
import es.gob.radarcovid.models.domain.ExposureInfo
import es.gob.radarcovid.models.domain.HealingTime
import org.junit.Assert.assertFalse
import org.junit.Test

class HomePresenterUnitTest {
    private val view: HomeView = mock()
    private val router: HomeRouter = mock()
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase = mock()
    private val exposureRadarUseCase: ExposureRadarUseCase = mock()
    private val exposureInfoUseCase: ExposureInfoUseCase = mock()
    private val fakeExposureInfoUseCase: FakeExposureInfoUseCase = mock()
    private val legalTermsUseCase: LegalTermsUseCase = mock()
    private val getHealingTimeUseCase: GetHealingTimeUseCase = mock()

    private val presenter: HomePresenter = HomePresenterImpl(
        view,
        router,
        onboardingCompletedUseCase,
        exposureRadarUseCase,
        exposureInfoUseCase,
        fakeExposureInfoUseCase,
        legalTermsUseCase,
        getHealingTimeUseCase
    )

    @Test
    fun whenEnvironmentIsProNeverInitializeFakeExposureButton() {
        whenever(fakeExposureInfoUseCase.getEnvironment()).thenReturn(Environment.PRO)

        presenter.viewReady(false)
        presenter.viewReady(true)

        verify(view, never()).setFakeExposureButton()
    }

    @Test
    fun whenEnvironmentIsProNeverFakeExposure() {
        whenever(fakeExposureInfoUseCase.getEnvironment()).thenReturn(Environment.PRO)

        presenter.onFakeExposureButtonClick()

        verify(fakeExposureInfoUseCase, never()).addFakeExposureDay()
    }

    @Test
    fun whenIsInfectedShowBackgroundEnabled() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.INFECTED
        })

        presenter.onResume()

        verify(view).showBackgroundEnabled(true)
    }

    @Test
    fun whenIsInfectedHideReportButton() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.INFECTED
        })

        presenter.onResume()

        verify(view).hideReportButton()
    }

    @Test
    fun whenExposureNotificationsDisabledRadarIsDisabled() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            exposureNotificationsEnabled = false
        })

        presenter.onResume()

        assertFalse(exposureRadarUseCase.isRadarEnabled())
    }

    @Test
    fun whenIsInfectedDisableRadarBlock() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.INFECTED
        })

        presenter.onResume()

        verify(view).setRadarBlockEnabled(false)
    }

    @Test
    fun whenIsInfectedShowExposureBlockInfected() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.INFECTED
        })

        presenter.onResume()

        verify(view).showExposureBlockInfected()
    }

    @Test
    fun whenExposureIsHighEnableRadarBlock() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.HIGH
        })

        whenever (getHealingTimeUseCase.getHealingTime()).thenReturn(HealingTime())

        presenter.onResume()

        verify(view).setRadarBlockEnabled(true)
    }

    @Test
    fun whenExposureIsHighShowReportButton() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.HIGH
        })

        whenever (getHealingTimeUseCase.getHealingTime()).thenReturn(HealingTime())

        presenter.onResume()

        verify(view).showReportButton()
    }

    @Test
    fun whenExposureIsHighShowExposureBlockHigh() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.HIGH
        })

        whenever (getHealingTimeUseCase.getHealingTime()).thenReturn(HealingTime())

        presenter.onResume()

        verify(view).showExposureBlockHigh(14)
    }

    @Test
    fun whenExposureIsLowEnableRadarBlock() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.LOW
        })

        presenter.onResume()

        verify(view).setRadarBlockEnabled(true)
    }

    @Test
    fun whenExposureIsLowShowReportButton() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.LOW
        })

        presenter.onResume()

        verify(view).showReportButton()
    }

    @Test
    fun whenExposureIsLowShowExposureBlockLow() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.LOW
        })

        presenter.onResume()

        verify(view).showExposureBlockLow()
    }

}