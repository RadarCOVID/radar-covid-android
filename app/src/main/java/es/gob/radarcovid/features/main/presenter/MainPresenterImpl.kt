/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.main.presenter

import es.gob.radarcovid.datamanager.usecase.MainUseCase
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.main.protocols.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val mainUseCase: MainUseCase
) : MainPresenter {

    override fun viewReady(activateRadar: Boolean) {
        router.navigateToHome(activateRadar)
    }

    override fun onResume() {
        Observable.fromCallable { mainUseCase.syncExposureInfo() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { }
    }

    override fun onHomeButtonClick() {
        router.navigateToHome(false)
    }

    override fun onProfileButtonClick() {
        router.navigateToProfile()
    }

    override fun onHelplineButtonClick() {
        router.navigateToHelpline()
    }

    override fun onSettingsButtonClick() {
        router.navigateToSettings()
    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

}