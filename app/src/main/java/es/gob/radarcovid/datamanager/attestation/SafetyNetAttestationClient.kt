/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.attestation

import android.content.Context
import android.util.Base64
import com.google.android.gms.safetynet.SafetyNet
import com.google.gson.Gson

class SafetyNetAttestationClient(
    private val context: Context,
    private val parameters: AttestationParameters
) : AttestationClient {
    data class AttestationParameters(
        internal val apiKey: String,
        internal val apkPackageName: String,
        internal val requiresBasicIntegrity: Boolean,
        internal val requiresCtsProfile: Boolean,
        internal val requiresHardwareAttestation: Boolean
    )

    override fun attest(onSuccess: (String?) -> Unit, onError: (Exception) -> Unit, nonce: String) {
        val nonceByteArray = Base64.decode(nonce, Base64.DEFAULT)
        SafetyNet.getClient(context).attest(nonceByteArray, parameters.apiKey)
            .addOnSuccessListener() {
                // Indicates communication with the service was successful.
                // Use response.getJwsResult() to get the result data.
                val payload = String(Base64.decode(it.jwsResult.split(".")[1], Base64.DEFAULT))

                var jsonResult: Map<String?, Any?> = HashMap()
                jsonResult = Gson().fromJson(
                    payload,
                    jsonResult.javaClass
                )

                if (nonce != jsonResult["nonce"]) {
                    onSuccess(null)
                } else if (parameters.apkPackageName != jsonResult["apkPackageName"]) {
                    onSuccess(null)
                } else if (parameters.requiresBasicIntegrity && jsonResult["basicIntegrity"] != true) {
                    onSuccess(null)
                } else if (parameters.requiresCtsProfile && jsonResult["ctsProfileMatch"] != true) {
                    onSuccess(null)
                } else if (parameters.requiresHardwareAttestation) {
                    val evaluationType = jsonResult["evaluationType"] as? String
                    if (evaluationType == null) {
                        onSuccess(null)
                    } else if (!evaluationType.contains("HARDWARE_BACKED")) {
                        onSuccess(null)
                    }
                } else {
                    onSuccess(it.jwsResult)
                }

            }
            .addOnFailureListener() { e ->
                // An error occurred while communicating with the service.
                onError(e)
            }
    }

}