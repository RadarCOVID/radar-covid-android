/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.presenter

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.gob.radarcovid.datamanager.usecase.ReportInfectedUseCase
import es.gob.radarcovid.features.covidreport.form.pages.step2.presenter.Step2MyHealthPresenterImp
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthRouter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthView
import es.gob.radarcovid.models.exception.NetworkUnavailableException
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test


class CovidReportPresenterUnitTest {

    private val view: Step2MyHealthView = mock {
        on { getReportCode() } doReturn ""
    }
    private val router: Step2MyHealthRouter = mock()
    private val reportInfectedUseCase: ReportInfectedUseCase = mock()
    private val presenter: Step2MyHealthPresenter =
        Step2MyHealthPresenterImp(view, router, reportInfectedUseCase)

    @Before
    fun init() {
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setErrorHandler { }
    }

    @Test
    fun onReportSuccessTest() {
        whenever(
            reportInfectedUseCase.reportInfected(
                "",
                null,
                0
            )
        ).thenReturn(Completable.complete())
        presenter.onSendButtonClick()

        verify(view).getReportCode()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).finish()
        verify(router).navigateToConfirmation()
    }

    @Test
    fun onReportDefaultErrorTest() {
        whenever(reportInfectedUseCase.reportInfected("", null, 0))
            .thenReturn(Completable.error(Exception()))
        presenter.onSendButtonClick()

        verify(view).hideLoadingWithErrorOnReport()
    }

    @Test
    fun onReportNetworkErrorTest() {
        whenever(reportInfectedUseCase.reportInfected("", null, 0))
            .thenReturn(Completable.error(NetworkUnavailableException()))
        presenter.onSendButtonClick()

        verify(view).hideLoadingWithNetworkError()
    }

}