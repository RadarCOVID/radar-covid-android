/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.HealingTime
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
import java.util.*

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

    fun getUuid(): String

    fun setUuid(uuid: String)

    fun getInfectionReportDate(): Date?

    fun setInfectionReportDate(date: Date)

    fun setExposed(exposed: Boolean)

    fun wasExposed(): Boolean

    fun setSelectedRegion(region: String)

    fun getSelectedRegion(): String

    fun setSelectedLanguage(language: String)

    fun getSelectedLanguage(): String

    fun setLabels(labels: Map<String, String>)

    fun getLabels(): Map<String, String>

    fun setLanguages(languages: List<Language>)

    fun getLanguages(): List<Language>

    fun setRegions(regions: List<Region>)

    fun getRegions(): List<Region>

    fun setHealingTime(healingTime: HealingTime)

    fun getHealingTime(): HealingTime

}