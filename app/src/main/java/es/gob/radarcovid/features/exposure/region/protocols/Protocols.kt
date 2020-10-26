/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.region.protocols

interface RegionInfoView {

    fun showRegionSelector(
        regions: List<String>,
        selectedRegionIndex: Int
    )

    fun showRegionInfo(name: String, phone: String, webName: String)

}

interface RegionInfoPresenter {

    fun viewReady()

    fun onPhoneButtonClick()

    fun onWebButtonClick()

    fun onRegionButtonClick()

    fun onRegionSelected(position: Int)

}

interface RegionInfoRouter {

    fun navigateToDialer(phone: String)

    fun navigateToBrowser(url: String)

}