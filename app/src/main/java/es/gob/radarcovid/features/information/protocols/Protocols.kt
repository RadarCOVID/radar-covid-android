/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.information.protocols

import java.util.*

interface InformationView {

    fun showHelpDialog()

    fun showRadarEnabled(isRadarEnabled: Boolean)

    fun showBluetoothEnabled()

    fun showLastUpdateDate(date: Date, daysElapsed: Int, locale: String)

    fun showTerminalData(locale: String)
    fun showExposureRecordDialog()
}

interface InformationPresenter {

    fun viewReady()

    fun onResume()

    fun onHelpButtonClick()

    fun onSupportMailClick(text: String, subject: String, email: String)

    fun onExposureRecodrClick()

}

interface InformationRouter {

    fun navigateToMail(text: String, subject: String, email: String)
}