/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.protocols

interface ExposureView {

    fun showExposureLevelLow()

    fun showExposureLevelHigh()

    fun showExposureLevelInfected()

    fun setExposureInfo(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    )

    fun setDaysToHeal(daysToHeal: Int)

    fun showExposureDates(exposureDates: String)

    fun hideExposureDates()

    fun setInfectionDates(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    )

    fun setLastUpdateNoData()

    fun showDialerForSupport()

    fun showExposureHealedDialog()

    fun showVenueExposureLevelHigh()

    fun setVenueExposureInfo(date: String, daysElapsed: Int?)

}

interface ExposurePresenter {

    fun viewReady()

    fun onResume(isVenueExposed: Boolean)

    fun onPause()

    fun onContactButtonClick()

    fun onReportButtonClick()

}

interface ExposureRouter {

    fun navigateToCovidReport()

}