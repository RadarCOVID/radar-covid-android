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

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.common.extensions.toJson
import es.gob.radarcovid.models.domain.HealingTime
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class PreferencesRepositoryImpl @Inject constructor(@Named("applicationContext") context: Context) :
    PreferencesRepository {

    companion object {
        private const val PREFERENCES_NAME = "app_preferences"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_UUID = "uuid"
        private const val KEY_INFECTION_REPORT_DATE = "key_infection_report_date"
        private const val KEY_WAS_EXPOSED = "key_was_exposed"
        private const val KEY_LABELS = "key_labels"
        private const val KEY_CURRENT_REGION = "key_current_region"
        private const val KEY_CURRENT_LANGUAGE = "key_current_language"
        private const val KEY_REGIONS = "key_regions"
        private const val KEY_LANGUAGES = "key_languages"
        private const val KEY_HEALING_TIME = "key_healing_time"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun isOnBoardingCompleted(): Boolean =
        preferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    override fun setOnboardingCompleted(onboardingCompleted: Boolean) {
        preferences
            .edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, onboardingCompleted)
            .apply()
    }

    override fun getUuid(): String =
        preferences.getString(KEY_UUID, "") ?: ""


    override fun setUuid(uuid: String) {
        preferences
            .edit()
            .putString(KEY_UUID, uuid)
            .apply()
    }

    override fun getInfectionReportDate(): Date? {
        val infectionReportDateMillis = preferences.getLong(KEY_INFECTION_REPORT_DATE, 0)
        return if (infectionReportDateMillis == 0L)
            null
        else
            Date(infectionReportDateMillis)
    }

    override fun setInfectionReportDate(date: Date) {
        preferences.edit()
            .putLong(KEY_INFECTION_REPORT_DATE, date.time)
            .apply()
    }

    override fun setExposed(exposed: Boolean) {
        preferences.edit()
            .putBoolean(KEY_WAS_EXPOSED, exposed)
            .apply()
    }

    override fun wasExposed(): Boolean =
        preferences.getBoolean(KEY_WAS_EXPOSED, false)

    override fun setSelectedRegion(region: String) {
        preferences.edit()
            .putString(KEY_CURRENT_REGION, region)
            .apply()
    }

    override fun getSelectedRegion(): String =
        preferences.getString(KEY_CURRENT_REGION, "ES-MD") ?: "ES-MD"

    override fun setSelectedLanguage(language: String) {
        preferences.edit()
            .putString(KEY_CURRENT_LANGUAGE, language)
            .apply()
    }

    override fun getSelectedLanguage(): String =
        preferences.getString(KEY_CURRENT_LANGUAGE, "es-ES") ?: "es-ES"

    override fun setLabels(labels: Map<String, String>) {
        preferences.edit()
            .putString(KEY_LABELS, labels.toJson())
            .apply()
    }

    override fun getLabels(): Map<String, String> {
        val itemType = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(
            preferences.getString(
                KEY_LABELS, "{\"test\":\"Hola Label\"}"
            ),
            itemType
        )
    }

    override fun setLanguages(languages: List<Language>) {
        preferences.edit()
            .putString(KEY_LANGUAGES, languages.toJson())
            .apply()
    }

    override fun getLanguages(): List<Language> {
        val itemType = object : TypeToken<List<Language>>() {}.type
        return Gson().fromJson(
            preferences.getString(
                KEY_LANGUAGES, "[]"
            ),
            itemType
        )
    }

    override fun setRegions(regions: List<Region>) {
        preferences.edit()
            .putString(KEY_REGIONS, regions.toJson())
            .apply()
    }

    override fun getRegions(): List<Region> {
        val itemType = object : TypeToken<List<Region>>() {}.type
        return Gson().fromJson(
            preferences.getString(
                KEY_REGIONS, "[]"
            ),
            itemType
        )
    }

    override fun setHealingTime(healingTime: HealingTime) {
        preferences.edit()
            .putString(KEY_HEALING_TIME, healingTime.toJson())
            .apply()
    }

    override fun getHealingTime(): HealingTime {
        val healingTimeJson = preferences.getString(
            KEY_HEALING_TIME,
            "{\n" +
                    "        \"exposureHighMinutes\": 20160,\n" +
                    "        \"infectedMinutes\": 43200\n" +
                    "    }"
        )
        return Gson().fromJson(healingTimeJson, HealingTime::class.java)
    }

}