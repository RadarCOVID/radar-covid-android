/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.base.utils

import es.gob.radarcovid.BuildConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.dpppt.android.sdk.util.SignatureUtil
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class JwtTokenUtils @Inject constructor() {

    fun getOnset(token: String): Date {
        val jwt = parseToken(token)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return formatter.parse(jwt.body["onset"] as? String ?: "") ?: Date()
    }

    private fun parseToken(signedJWT: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(SignatureUtil.getPublicKeyFromBase64(BuildConfig.PUBLIC_KEY_VERIFICATION))
            .build()
            .parseClaimsJws(signedJWT)
    }

}