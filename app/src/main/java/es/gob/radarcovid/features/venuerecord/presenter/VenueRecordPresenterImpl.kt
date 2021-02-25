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
import javax.inject.Inject

class VenueRecordPresenterImpl  @Inject constructor(
    private val view: VenueRecordView,
    private val venueRecordUseCase: VenueRecordUseCase
) : VenueRecordPresenter {

    companion object {
        const val ERROR_CAPTURED_CODE_FRAGMENT = 0
        const val CHECK_IN_FRAGMENT = 1
        const val INITIATED_RECORD_FRAGMENT = 2
        const val CHECK_OUT_FRAGMENT = 3
        const val RECORD_SUCCESS_FRAGMENT = 4
    }

    override fun viewReady() {
        if (venueRecordUseCase.isRecordInProgress()) {
            view.showFragment(INITIATED_RECORD_FRAGMENT)
        } else {
            startScan()
        }
    }

    override fun onContinueButtonClick(pageIndex: Int) {
        when (pageIndex) {
            ERROR_CAPTURED_CODE_FRAGMENT -> {
                //Retry Scan
                startScan()
            }
            CHECK_IN_FRAGMENT -> {
                //do check in
                doCheckIn()
            }
            INITIATED_RECORD_FRAGMENT -> {
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
            ERROR_CAPTURED_CODE_FRAGMENT -> {
                view.exit()
            }
            CHECK_IN_FRAGMENT -> {
                view.exit()
            }
            INITIATED_RECORD_FRAGMENT -> {
                view.exit()
            }
            CHECK_OUT_FRAGMENT -> {
                view.showFragment(INITIATED_RECORD_FRAGMENT)
            }
            RECORD_SUCCESS_FRAGMENT -> {
                view.exit()
            }
        }
    }

    override fun onOkScan(data: String) {
        view.showFragment(CHECK_IN_FRAGMENT)
    }

    override fun onErrorScan() {
        view.showFragment(ERROR_CAPTURED_CODE_FRAGMENT)
    }

    override fun cancelRecord() {
        venueRecordUseCase.setRecordInProgress(false)
    }

    private fun startScan() {
        view.startQRScan()
    }

    private fun doCheckIn() {
        venueRecordUseCase.setRecordInProgress(true)
        view.showFragment(INITIATED_RECORD_FRAGMENT)
    }

    private fun navigateToCheckOut() {
        view.showFragment(CHECK_OUT_FRAGMENT)
    }

    private fun doCheckOut() {
        venueRecordUseCase.setRecordInProgress(false)
        view.showFragment(RECORD_SUCCESS_FRAGMENT)
    }
}