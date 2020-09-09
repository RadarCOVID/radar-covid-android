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
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.ExposureRadarUseCase
import es.gob.radarcovid.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeRouter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.models.domain.ExposureInfo
import org.junit.Assert.assertFalse
import org.junit.Test

class HomePresenterUnitTest {
    private val view: HomeView = mock()
    private val router: HomeRouter = mock()
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase = mock()
    private val exposureRadarUseCase: ExposureRadarUseCase = mock()
    private val exposureInfoUseCase: ExposureInfoUseCase = mock()

    private val presenter: HomePresenter = HomePresenterImpl(
        view,
        router,
        onboardingCompletedUseCase,
        exposureRadarUseCase,
        exposureInfoUseCase,
        mock()
    )

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

        presenter.onResume()

        verify(view).setRadarBlockEnabled(true)
    }

    @Test
    fun whenExposureIsHighShowReportButton() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.HIGH
        })

        presenter.onResume()

        verify(view).showReportButton()
    }

    @Test
    fun whenExposureIsHighShowExposureBlockHigh() {
        whenever(exposureInfoUseCase.getExposureInfo()).thenReturn(ExposureInfo().apply {
            level = ExposureInfo.Level.HIGH
        })

        presenter.onResume()

        verify(view).showExposureBlockHigh()
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