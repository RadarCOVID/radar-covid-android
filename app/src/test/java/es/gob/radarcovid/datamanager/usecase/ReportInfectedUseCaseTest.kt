/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.usecase

import com.nhaarman.mockitokotlin2.*
import es.gob.radarcovid.common.base.utils.JwtTokenUtils
import es.gob.radarcovid.datamanager.repository.ApiRepository
import es.gob.radarcovid.datamanager.repository.ContactTracingRepository
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.models.response.ResponseToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.funktionale.either.Either
import org.junit.Before
import org.junit.Test
import java.util.*

class ReportInfectedUseCaseTest {

    private val contactTracingRepository: ContactTracingRepository =
        mock {
            on { notifyInfected(any(), any()) } doReturn Completable.complete()
        }
    private val preferencesRepository: PreferencesRepository = mock()
    private val apiRepository: ApiRepository = mock {
        on { verifyCode(any(), any()) } doReturn Either.right(ResponseToken(""))
    }
    private val jwtTokenUtils: JwtTokenUtils = mock {
        on { getOnset(any()) } doReturn Date()
    }
    private val reportInfectedUseCase =
        ReportInfectedUseCase(
            contactTracingRepository,
            preferencesRepository,
            apiRepository,
            jwtTokenUtils
        )

    @Before
    fun init() {
        RxJavaPlugins.setErrorHandler { }
    }

    @Test
    fun assertReportUpstreamCompletedNormally() {
        reportInfectedUseCase.reportInfected("", null, 0).test()
            .assertResult()
    }

    @Test
    fun onReportInfectedThenVerifyCode() {

        reportInfectedUseCase.reportInfected("", null, 0).subscribe()

        verify(apiRepository).verifyCode(any(), any())

    }

    @Test
    fun onCodeVerifiedThenGenerateOnsetDate() {

        reportInfectedUseCase.reportInfected("", null, 0).subscribe()

        inOrder(apiRepository, jwtTokenUtils).apply {
            verify(apiRepository).verifyCode(any(), any())
            verify(jwtTokenUtils).getOnset(any())
        }

    }

    @Test
    fun onReportInfectedThenNotifyInfected() {

        reportInfectedUseCase.reportInfected("", null, 0).subscribe()

        verify(contactTracingRepository).notifyInfected(any(), any())

    }

    @Test
    fun onNotifyInfectedThenSaveTheInfectionReportDate() {

        reportInfectedUseCase.reportInfected("", null, 0).subscribe()

        inOrder(contactTracingRepository, preferencesRepository).apply {
            verify(contactTracingRepository).notifyInfected(any(), any())
            verify(preferencesRepository).setInfectionReportDate(any())
        }

    }

}