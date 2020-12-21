/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.settings.presenter

import es.gob.radarcovid.features.settings.protocols.SettingsPresenter
import es.gob.radarcovid.features.settings.protocols.SettingsRouter
import javax.inject.Inject

class SettingsPresenterImpl @Inject constructor(
    private val router: SettingsRouter
) : SettingsPresenter {

    override fun viewReady() {

    }

    override fun onInformationClick() {
        router.navigateToInformation()
    }

}