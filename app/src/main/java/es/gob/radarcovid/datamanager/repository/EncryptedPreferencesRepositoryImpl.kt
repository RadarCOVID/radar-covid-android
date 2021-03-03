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

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.common.extensions.toJson
import es.gob.radarcovid.models.domain.VenueRecord
import javax.inject.Inject

class EncryptedPreferencesRepositoryImpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences?
) : EncryptedPreferencesRepository {

    companion object {
        private const val KEY_CURRENT_VENUE_RECORD = "key_current_venue_record"
        private const val KEY_LIST_VENUE_RECORD = "key_list_venue_record"
    }


//    private var sharedPreferences: SharedPreferences? = null
//
//    private fun encryptedPreferences(context: Context): SharedPreferences? {
//        try {
//
//            if (sharedPreferences == null) {
//                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
//                    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
//                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                    .setKeySize(KEY_SIZE)
//                    .build()
//
//                val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
//                    .setKeyGenParameterSpec(keyGenParameterSpec)
//                    .build()
//
//                sharedPreferences = EncryptedSharedPreferences.create(
//                    context,
//                    ENCRYPTED_PREFERENCES_NAME,
//                    masterKeyAlias,
//                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//
//                )
//            }
//
//            return sharedPreferences!!
//
//        } catch (ex: Exception) {
//            return null
//        }
//    }
//
//    private val preferences = encryptedPreferences(context)

    private fun saveToPrefs(key: String, data: String) {
        encryptedSharedPreferences?.edit()?.putString(key, data)?.apply()
    }

    private fun getFromPreferences(key: String): String? =
        encryptedSharedPreferences?.getString(key, null)

    private fun removeFromPrefs(key: String) {
        encryptedSharedPreferences?.edit()?.remove(key)?.apply()
    }

    override fun getCurrentVenue(): VenueRecord? {
        val venueRecord = getFromPreferences(KEY_CURRENT_VENUE_RECORD)
        return if (venueRecord != null)
            Gson().fromJson(venueRecord, VenueRecord::class.java)
        else null
    }

    override fun setCurrentVenueRecord(current: VenueRecord) {
        saveToPrefs(KEY_CURRENT_VENUE_RECORD, current.toJson())
    }

    override fun removeCurrentVenue() {
        removeFromPrefs(KEY_CURRENT_VENUE_RECORD)
    }

    override fun getVisitedVenue(): List<VenueRecord> {
        val list = getFromPreferences(KEY_LIST_VENUE_RECORD)
        return if (list != null) {
            val itemType = object : TypeToken<List<VenueRecord>>() {}.type
            Gson().fromJson(
                list,
                itemType
            )
        } else {
            emptyList()
        }
    }

    override fun setVisitedVenue(venues: List<VenueRecord>) {
        saveToPrefs(KEY_LIST_VENUE_RECORD, venues.toJson())
    }
}