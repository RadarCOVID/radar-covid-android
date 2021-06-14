/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.stats.protocols

import es.gob.radarcovid.common.view.RequestView

interface StatsView : RequestView {

    fun showCountriesDialog(countries: List<String>)

    fun setDataView(countries: String, date: String, downloads: String, contagions: String)
}

interface StatsPresenter {

    fun viewReady()

    fun onCountriesClick()
}

interface StatsRouter {

}