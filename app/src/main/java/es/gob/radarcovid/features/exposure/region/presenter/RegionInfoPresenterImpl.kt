/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.region.presenter

import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoPresenter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoRouter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoView
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class RegionInfoPresenterImpl @Inject constructor(
    private val view: RegionInfoView,
    private val router: RegionInfoRouter,
    getLocaleInfoUseCase: GetLocaleInfoUseCase
) : RegionInfoPresenter {

    private val localeInfo: LocaleInfo = getLocaleInfoUseCase.getLocaleInfo()

    private var selectedRegionIndex: Int = 0

    override fun viewReady() {

    }

    override fun onPhoneButtonClick() {
        router.navigateToDialer(localeInfo.regions[selectedRegionIndex].phone)
    }

    override fun onWebButtonClick() {
        router.navigateToBrowser(localeInfo.regions[selectedRegionIndex].url)
    }

    override fun onRegionButtonClick() {
        view.showRegionSelector(localeInfo.regions.map { it.name }, selectedRegionIndex)
    }

    override fun onRegionSelected(position: Int) {
        selectedRegionIndex = position

    }

    override fun onRegionSelectorAcceptButtonClick() {
        with(localeInfo.regions[selectedRegionIndex]) {
            view.showRegionInfo(name, phone, webName, url)
        }
    }
}