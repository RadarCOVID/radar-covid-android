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

import es.gob.radarcovid.datamanager.usecase.*
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.main.protocols.MainView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val mainUseCase: MainUseCase,
    private val getReminderTimeUseCase: GetReminderTimeUseCase,
    private val sendAnalyticsUseCase: SendAnalyticsUseCase,
    private val getLocaleInfoUseCase: GetLocaleInfoUseCase,
    private val venueRecordUseCase: VenueRecordUseCase
) : MainPresenter {

    override fun viewReady(activateRadar: Boolean) {
        view.cancelNotificationReminder()
        view.startAnalyticsWorker(sendAnalyticsUseCase.getAnalyticsPeriod())
        if (getLocaleInfoUseCase.isLanguageChanged()) {
            getLocaleInfoUseCase.resetLanguageChanged()
            view.setSettingSelected()
            router.navigateToSettings()
        } else {
            router.navigateToHome(activateRadar, false)
        }
    }

    override fun onResume(isVenueRecordSelected: Boolean) {
        Observable.fromCallable { mainUseCase.syncExposureInfo() }
            .subscribeOn(Schedulers.io())
            .subscribe()
        updateMenuIcon()
        if (isVenueRecordSelected) {
            if (venueRecordUseCase.isCurrentVenue()) {
                //navigate to home
                view.setHomeSelected()
                router.navigateToHome(activateRadar = false, manualNavigation = false)
            } else {
                router.navigateToVenueRecord(false)
            }
        }
    }

    override fun onStop() {
        view.createNotificationReminder(getReminderTimeUseCase.getReminderTime())
    }

    override fun onRestart() {
        view.cancelNotificationReminder()
    }

    override fun onHomeButtonClick() {
        router.navigateToHome(false, true)
    }

    override fun onProfileButtonClick() {
        router.navigateToProfile()
    }

    override fun onVenueButtonClick() {
        router.navigateToVenueRecord(venueRecordUseCase.isCurrentVenue())
    }

    override fun onHelplineButtonClick() {
        router.navigateToHelpline()
    }

    override fun onStatsButtonClick() {
        router.navigateToStats()
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

    private fun updateMenuIcon() {
        view.updateVenueIcon(venueRecordUseCase.isCurrentVenue())
    }

}