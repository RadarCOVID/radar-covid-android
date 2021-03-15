/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.presenter

import es.gob.radarcovid.common.extensions.addHours
import es.gob.radarcovid.common.extensions.addMinutes
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.features.venuerecord.pages.checkout.presenter.VenueTimeOut
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class VenueRecordPresenterImpl @Inject constructor(
    private val view: VenueRecordView,
    private val venueRecordUseCase: VenueRecordUseCase,
    private val preferencesRepository: PreferencesRepository
) : VenueRecordPresenter {

    companion object {
        const val ERROR_CAPTURED_CODE_FRAGMENT = 0
        const val CONFIRM_RECORD_FRAGMENT = 1
        const val CHECK_IN_FRAGMENT = 2
        const val CHECK_OUT_FRAGMENT = 3
        const val RECORD_SUCCESS_FRAGMENT = 4
    }

    private var qrCaptured: String = ""
    private var venueName: String = ""
    private lateinit var timeOut: VenueTimeOut

    override fun onResume(pageIndex: Int) {
        //Back to app after autocheckout
        if (pageIndex == CHECK_IN_FRAGMENT && !venueRecordUseCase.isCurrentVenue()) {
            view.exit()
        }
    }

    override fun viewReady() {
        if (venueRecordUseCase.isCurrentVenue()) {
            view.showFragment(CHECK_IN_FRAGMENT)
        } else {
            startScan()
        }
    }

    override fun getCurrentVenueName(): String = venueName

    override fun setVenueTimeOut(venueTimeOut: VenueTimeOut) {
        timeOut = venueTimeOut
    }

    override fun onContinueButtonClick(pageIndex: Int) {
        when (pageIndex) {
            ERROR_CAPTURED_CODE_FRAGMENT -> {
                //Retry Scan
                startScan()
            }
            CONFIRM_RECORD_FRAGMENT -> {
                //do check in
                doCheckIn()
            }
            CHECK_IN_FRAGMENT -> {
                //Go to check out
                navigateToCheckOut()
            }
            CHECK_OUT_FRAGMENT -> {
                //do CheckOut
                doCheckOut()
            }
        }
    }

    override fun onBackPressed(pageIndex: Int) {
        when (pageIndex) {
            CHECK_OUT_FRAGMENT ->
                view.showFragment(CHECK_IN_FRAGMENT)
            else ->
                view.exit()
        }
    }

    override fun onOkScan(data: String) {
        qrCaptured = data
        try {
            val venueInfo = venueRecordUseCase.getVenueInfo(qrCaptured)
            venueName = venueInfo.name
            view.showFragment(CONFIRM_RECORD_FRAGMENT)
        } catch (e: Exception) {
            view.showFragment(ERROR_CAPTURED_CODE_FRAGMENT)
        }

    }

    override fun onErrorScan() {
        view.showFragment(ERROR_CAPTURED_CODE_FRAGMENT)
    }

    override fun cancelRecord() {
        venueRecordUseCase.cancelCheckIn()
        view.cancelVenueRecordWorker()
    }

    private fun startScan() {
        view.startQRScan()
    }

    private fun doCheckIn() {
        venueRecordUseCase.checkIn(qrCaptured)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onCheckInSuccess() },
                { onCheckInError(it) }
            )
    }

    private fun onCheckInSuccess() {
        view.startVenueRecordWorker(preferencesRepository.getRecordNotificationTime())
        view.showFragment(CHECK_IN_FRAGMENT)
    }

    private fun onCheckInError(error: Throwable) {
        view.showError(error, true)
    }

    private fun navigateToCheckOut() {
        view.showFragment(CHECK_OUT_FRAGMENT)
    }

    private fun doCheckOut() {

        //Calculate time out
        val currentVenue = venueRecordUseCase.getCurrentVenue()

        val hours = when (timeOut) {
            VenueTimeOut.OPT_30 -> 30 //Minutes
            VenueTimeOut.OPT_1 -> 1
            VenueTimeOut.OPT_2 -> 2
            VenueTimeOut.OPT_4 -> 3
            VenueTimeOut.OPT_5 -> 4
            else -> 0  //NOW
        }
        val dateOut =
            if (hours > 5) currentVenue?.dateIn?.addMinutes(hours)
            else currentVenue?.dateIn?.addHours(hours)

        venueRecordUseCase.checkOut(dateOut ?: Date())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onCheckOutSuccess() },
                { onCheckOutError(it) }
            )
        view.showFragment(RECORD_SUCCESS_FRAGMENT)
    }

    private fun onCheckOutSuccess() {
        view.cancelVenueRecordWorker()
        view.showFragment(RECORD_SUCCESS_FRAGMENT)
    }

    private fun onCheckOutError(error: Throwable) {
        //TODO: ¿cancel record on error?
        view.showError(error, true)
    }
}