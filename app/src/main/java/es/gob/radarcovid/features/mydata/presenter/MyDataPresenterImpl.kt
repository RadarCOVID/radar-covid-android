/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.mydata.presenter

import es.gob.radarcovid.features.mydata.protocols.MyDataPresenter
import es.gob.radarcovid.features.mydata.protocols.MyDataRouter
import javax.inject.Inject

class MyDataPresenterImpl @Inject constructor(
    private val router: MyDataRouter
) : MyDataPresenter {

    override fun viewReady() {

    }
    
    override fun onConditionsButtonClick() {
        router.navigateToConditions()
    }

    override fun onPrivacyButtonClick() {
        router.navigateToPrivacyPolicy()
    }

}