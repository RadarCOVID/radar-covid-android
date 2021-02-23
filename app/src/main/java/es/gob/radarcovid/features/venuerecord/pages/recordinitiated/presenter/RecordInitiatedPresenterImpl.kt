/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.recordinitiated.presenter

import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.protocols.RecordInitiatedPresenter
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.protocols.RecordInitiatedView
import javax.inject.Inject

class RecordInitiatedPresenterImpl  @Inject constructor(
    private val view: RecordInitiatedView
) : RecordInitiatedPresenter {

    override fun onContinueButtonClick() {
        view.performContinueButtonClick()
    }
}