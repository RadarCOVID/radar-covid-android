/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.information.presenter

import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.ExposureRadarUseCase
import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.information.protocols.InformationPresenter
import es.gob.radarcovid.features.information.protocols.InformationRouter
import es.gob.radarcovid.features.information.protocols.InformationView
import es.gob.radarcovid.models.domain.ExposureInfo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InformationPresenterImpl @Inject constructor(
    private val view: InformationView,
    private val exposureInfoUseCase: ExposureInfoUseCase,
    private val exposureRadarUseCase: ExposureRadarUseCase,
    private val localeInfoUseCase: GetLocaleInfoUseCase,
    private val router: InformationRouter
) : InformationPresenter {

    override fun viewReady() {

    }


    override fun onResume() {
        updateViews(exposureInfoUseCase.getExposureInfo())
    }

    override fun onHelpButtonClick() {
        view.showHelpDialog()
    }

    override fun onSupportMailClick(text: String, subject: String, email: String) {
        router.navigateToMail(text, subject, email)
    }

    override fun onExposureRecodrClick() {
        view.showExposureRecordDialog()
    }

    private fun updateViews(exposureInfo: ExposureInfo) {
        view.showRadarEnabled(exposureRadarUseCase.isRadarEnabled())
        view.showBluetoothEnabled()

        val millisElapsed = System.currentTimeMillis() - exposureInfo.lastUpdateTime.time
        val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
        view.showLastUpdateDate(
            exposureInfo.lastUpdateTime,
            daysElapsed.toInt(),
            localeInfoUseCase.getSelectedLanguageForLocale()
        )
        view.showTerminalData(localeInfoUseCase.getSelectedLanguageForLocale())
    }
}