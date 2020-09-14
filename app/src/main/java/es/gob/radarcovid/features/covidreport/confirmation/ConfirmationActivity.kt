/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.base.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_confirmation.*
import javax.inject.Inject

class ConfirmationActivity : BaseBackNavigationActivity() {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ConfirmationActivity::class.java))
        }

    }

    @Inject
    lateinit var navigationUtils: NavigationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        initViews()
    }

    private fun initViews() {
        textViewConfirmationMoreInfo.setOnClickListener {
            navigationUtils.navigateToBrowser(
                this,
                labelManager.getText(
                    "MY_HEALTH_REPORTED_MORE_INFO_URL",
                    R.string.confirmation_more_info_url
                ).toString()
            )
        }
        buttonMoreInfo.setOnClickListener {
            navigationUtils.navigateToBrowser(
                this,
                labelManager.getText(
                    "MY_HEALTH_REPORTED_INFO_URL",
                    R.string.exposure_detail_info_url
                )
                    .toString()
            )
        }
    }

}
