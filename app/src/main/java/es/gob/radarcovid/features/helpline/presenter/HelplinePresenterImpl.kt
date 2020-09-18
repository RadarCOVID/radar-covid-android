/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.helpline.presenter

import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import javax.inject.Inject

class HelplinePresenterImpl @Inject constructor(
    private val view: HelplineView,
    private val router: HelplineRouter
) : HelplinePresenter {

    override fun viewReady() {

    }

    override fun onContactSupportButtonClick() {
        view.showDialerForSupport()
    }

    override fun onUrlButtonClick(url: String) {
        router.navigateToBrowser(url)
    }

}