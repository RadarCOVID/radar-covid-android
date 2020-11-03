/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.splash.presenter

import android.os.Handler
import es.gob.radarcovid.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.radarcovid.datamanager.usecase.SplashUseCase
import es.gob.radarcovid.features.splash.protocols.SplashPresenter
import es.gob.radarcovid.features.splash.protocols.SplashRouter
import es.gob.radarcovid.features.splash.protocols.SplashView
import es.gob.radarcovid.models.domain.InitializationData
import es.gob.radarcovid.models.exception.NetworkUnavailableException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val splashUseCase: SplashUseCase
) : SplashPresenter {

    private var isWaitingToStart: Boolean = false
    private var isInitializationCompleted: Boolean = false
    private var activateRadar: Boolean = false

    override fun viewReady(activateRadar: Boolean) {
        this.activateRadar = activateRadar
        requestInitialization()
    }

    override fun onResume() {
        if (isInitializationCompleted) {
            splashUseCase.checkGaenAvailability { available ->
                if (available)
                    navigateToHomeWithDelay()
                else
                    view.showPlayServicesRequiredDialog()
            }
        }
    }

    override fun onNetworkRetryButtonClick() {
        requestInitialization()
    }

    override fun onNetworkDialogCloseButtonClick() {
        view.finish()
    }

    override fun onInstallPlayServicesButtonClick() {
        router.navigateToPlayServicesPage()
    }

    override fun onUpdateAppButtonClick() {
        router.navigateToPlayStore()
        view.finish()
    }

    private fun requestInitialization() {
        splashUseCase.getInitializationObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onInitializationSuccess(it) },
                { onInitializationError(it) }
            )

    }

    private fun onInitializationSuccess(initializationData: InitializationData) {
        val versionCode = splashUseCase.getVersionCode()
        val settings = initializationData.settings

        view.reloadLabels()

        if (versionCode < settings.appInfo.minVersionCode) {
            view.showNeedUpdateDialog()
        } else {
            isInitializationCompleted = true
            onResume()
        }
    }

    private fun onInitializationError(error: Throwable) {
        if (error is NetworkUnavailableException) {
            view.showNoInternetWarning()
        } else {
            view.showError(error, true)
        }
    }

    private fun navigateToHomeWithDelay() {
        if (!isWaitingToStart) {
            isWaitingToStart = true
            Handler().postDelayed({
                if (onboardingCompletedUseCase.isOnBoardingCompleted())
                    router.navigateToMain(activateRadar)
                else
                    router.navigateToOnboarding()
                view.finish()
            }, 2000)
        }
    }

}