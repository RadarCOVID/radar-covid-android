/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.base.Constants.ENCRYPTED_PREFERENCES_KEY_SIZE
import es.gob.radarcovid.common.base.Constants.ENCRYPTED_PREFERENCES_NAME
import es.gob.radarcovid.common.di.scope.PerApplication
import javax.inject.Named

@Module
class EncryptedSharedPreferencesModule {


    @Provides
    @PerApplication
    fun providesEncryptedSharedPreferences(@Named("applicationContext") application: Context): SharedPreferences? {
        try {

            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(ENCRYPTED_PREFERENCES_KEY_SIZE)
                .build()

            val masterKeyAlias = MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyGenParameterSpec(keyGenParameterSpec)
                .build()

            return EncryptedSharedPreferences.create(
                application,
                ENCRYPTED_PREFERENCES_NAME,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

            )

        } catch (ex: Exception) {
            return null
        }
    }


}