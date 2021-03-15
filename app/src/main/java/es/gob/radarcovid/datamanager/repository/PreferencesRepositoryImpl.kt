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
import es.gob.radarcovid.common.base.Constants.ANALYTICS_PERIOD_DEFAULT
import es.gob.radarcovid.common.base.Constants.NOTIFICATION_REMINDER_DEFAULT
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
        private const val KEY_INFECTION_REPORT_DATE = "key_infection_report_date"
        private const val KEY_WAS_EXPOSED = "key_was_exposed"
        private const val KEY_LABELS = "key_labels"
        private const val KEY_CURRENT_REGION = "key_current_region"
        private const val KEY_CURRENT_LANGUAGE = "key_current_language"
        private const val KEY_REGIONS = "key_regions"
        private const val KEY_LANGUAGES = "key_languages"
        private const val KEY_HEALING_TIME = "key_healing_time"
        private const val KEY_T_DUMMY = "KEY_T_DUMMY"
        private const val KEY_SAVED_LEGAL_TERMS_VERSION = "key_saved_legal_terms_version"
        private const val KEY_SETTINGS_LEGAL_TERMS_VERSION = "key_settings_legal_terms_version"
        private const val KEY_RADARCOVID_DOWNLOAD_URL = "key_radarcovid_download_url"
        private const val KEY_NOTIFICATION_REMINDER = "key_notification_reminder"
        private const val KEY_EXPOSURE_ANALYTICS_COUNT = "key_exposure_analytics_count"
        private const val KEY_ANALYTICS_PERIOD = "key_analytics_period"
        private const val KEY_LANGUAGE_CHANGED = "key_language_changed"
        private const val KEY_RECORD_IN_PROGRESS = "key_record_in_progress"
        private const val KEY_APP_ACTIVE = "key_app_active"
        private const val KEY_BUNDLE_TAG = "key_bundle_tag"
        private const val KEY_VENUE_RECORD_NOTIFICATION = "key_venue_record_notification"
        private const val KEY_VENUE_AUTO_CHECK = "key_venue_auto_check"
        private const val KEY_VENUE_TROUBLE_PLACE_CHECK = "key_venue_trouble_place_check"
        private const val KEY_VENUE_QUARANTINE = "key_venue_quarantine"
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
        preferences.getString(KEY_CURRENT_LANGUAGE, "") ?: ""

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

    override fun getTDummy(): Long {
        return preferences.getLong(KEY_T_DUMMY, -1)
    }

    override fun setTDummy(time: Long) {
        preferences.edit().putLong(KEY_T_DUMMY, time).apply()
    }

    override fun setSavedLegalTermsVersion(version: String) {
        preferences.edit().putString(KEY_SAVED_LEGAL_TERMS_VERSION, version).apply()
    }

    override fun getSavedLegalTermsVersion(): String {
        return preferences.getString(KEY_SAVED_LEGAL_TERMS_VERSION, "") ?: ""
    }

    override fun setSettingsLegalTermsVersion(version: String) {
        preferences.edit().putString(KEY_SETTINGS_LEGAL_TERMS_VERSION, version).apply()
    }

    override fun getSettingsLegalTermsVersion(): String {
        return preferences.getString(KEY_SETTINGS_LEGAL_TERMS_VERSION, "") ?: ""
    }

    override fun setRadarCovidDownloadUrl(url: String) {
        preferences.edit().putString(KEY_RADARCOVID_DOWNLOAD_URL, url).apply()
    }

    override fun getRadarCovidDownloadUrl(): String {
        return preferences.getString(KEY_RADARCOVID_DOWNLOAD_URL, "") ?: ""
    }

    override fun setNotificationReminder(time: Int) {
        preferences.edit().putInt(KEY_NOTIFICATION_REMINDER, time).apply()
    }

    override fun getNotificationReminder(): Int {
        return preferences.getInt(KEY_NOTIFICATION_REMINDER, NOTIFICATION_REMINDER_DEFAULT)
    }

    override fun setExposureAnalyticsCount(count: Int) {
        preferences.edit().putInt(KEY_EXPOSURE_ANALYTICS_COUNT, count).apply()
    }

    override fun getExposureAnalyticsCount(): Int {
        return preferences.getInt(KEY_EXPOSURE_ANALYTICS_COUNT, 0)
    }

    override fun setAnalyticsPeriod(time: Int) {
        preferences.edit().putInt(KEY_ANALYTICS_PERIOD, time).apply()
    }

    override fun getAnalyticsPeriod(): Int {
        return preferences.getInt(KEY_ANALYTICS_PERIOD, ANALYTICS_PERIOD_DEFAULT)
    }

    override fun setLanguageChanged(changed: Boolean) {
        preferences.edit()
            .putBoolean(KEY_LANGUAGE_CHANGED, changed)
            .apply()
    }

    override fun getLanguageChanged(): Boolean =
        preferences.getBoolean(KEY_LANGUAGE_CHANGED, false)

    override fun setRecordInProgress(recordInProgress: Boolean) {
        preferences.edit()
            .putBoolean(KEY_RECORD_IN_PROGRESS, recordInProgress)
            .apply()
    }

    override fun isRecordInProgress(): Boolean =
        preferences.getBoolean(KEY_RECORD_IN_PROGRESS, false)

    override fun setApplicationActive(active: Boolean) {
        preferences.edit()
            .putBoolean(KEY_APP_ACTIVE, active)
            .apply()
    }

    override fun isApplicationActive(): Boolean =
        preferences.getBoolean(KEY_APP_ACTIVE, false)

    override fun setLastKeyBundleTag(keyBundleTag: Long) {
        preferences.edit().putLong(KEY_BUNDLE_TAG, keyBundleTag).apply()
    }

    override fun getLastKeyBundleTag(): Long =
        preferences.getLong(KEY_BUNDLE_TAG, 0L)

    override fun setRecordNotificationTime(time: Int) {
        preferences.edit().putInt(KEY_VENUE_RECORD_NOTIFICATION, time).apply()
    }

    override fun getRecordNotificationTime(): Int =
        preferences.getInt(KEY_VENUE_RECORD_NOTIFICATION, 60)

    override fun setAutoCheckoutTime(time: Int) {
        preferences.edit().putInt(KEY_VENUE_AUTO_CHECK, time).apply()
    }

    override fun getAutoCheckoutTime(): Int =
        preferences.getInt(KEY_VENUE_AUTO_CHECK, 300)

    override fun setTroubledPlaceCheckTime(time: Int) {
        preferences.edit().putInt(KEY_VENUE_TROUBLE_PLACE_CHECK, time).apply()
    }

    override fun getTroubledPlaceCheckTime(): Int =
        preferences.getInt(KEY_VENUE_TROUBLE_PLACE_CHECK, 120)

    override fun setQuarantineAfterVenueExposedTime(time: Int) {
        preferences.edit().putInt(KEY_VENUE_QUARANTINE, time).apply()
    }

    override fun getQuarantineAfterVenueExposedTime(): Int =
        preferences.getInt(KEY_VENUE_QUARANTINE, 2880)
}