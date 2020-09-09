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

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import es.gob.radarcovid.R
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.utils.LabelManager
import javax.inject.Inject

@PerApplication
class NavigationUtils @Inject constructor(
    private val labelManager: LabelManager
) {

    fun navigateToBrowser(context: Context, url: String) {
        val uri: Uri = if (url.contains("http://") || url.contains("https://")) {
            Uri.parse(url)
        } else {
            Uri.parse("http://$url")
        }
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    uri
                )
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                labelManager.getText(
                    "ALERT_NEED_BROWSER_CONTENT",
                    R.string.warning_need_browser_message
                ),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}