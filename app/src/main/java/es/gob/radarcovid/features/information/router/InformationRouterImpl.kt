/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.information.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.features.information.protocols.InformationRouter
import javax.inject.Inject

class InformationRouterImpl @Inject constructor(
    private val context: Context
) : InformationRouter {

    override fun navigateToMail(text: String, subject: String, email: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            type = "message/rfc822"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}