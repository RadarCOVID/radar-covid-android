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

import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class VenueRecordPresenterImpl @Inject constructor(
    private val view: VenueRecordView,
    private val venueRecordUseCase: VenueRecordUseCase
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

    override fun viewReady() {
        if (venueRecordUseCase.isCurrentVenue()) {
            view.showFragment(CHECK_IN_FRAGMENT)
        } else {
            startScan()
        }
    }

    override fun getCurrentVenueName(): String = venueName

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
    }

    private fun startScan() {
        view.startQRScan()
    }

    private fun doCheckIn() {
        venueRecordUseCase.checkIn(qrCaptured)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onCheckInSuccess(it) },
                { onCheckInError(it) }
            )
    }

    private fun onCheckInSuccess(result: Boolean) {
        view.showFragment(CHECK_IN_FRAGMENT)
    }

    private fun onCheckInError(error: Throwable) {
        view.showError(error, true)
    }

    private fun navigateToCheckOut() {
        view.showFragment(CHECK_OUT_FRAGMENT)
    }

    private fun doCheckOut() {
        venueRecordUseCase.checkOut()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onCheckOutSuccess(it) },
                { onCheckOutError(it) }
            )
        view.showFragment(RECORD_SUCCESS_FRAGMENT)
    }

    private fun onCheckOutSuccess(result: Boolean) {
        view.showFragment(RECORD_SUCCESS_FRAGMENT)
    }

    private fun onCheckOutError(error: Throwable) {
        //TODO: ¿cancel record on error?
        view.showError(error, true)
    }
}