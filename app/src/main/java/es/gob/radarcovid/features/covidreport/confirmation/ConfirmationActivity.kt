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
import android.view.View
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.base.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
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
        imageButtonBack.contentDescription =
            "${labelManager.getText("ACC_HOME_TITLE", R.string.title_home)} ${
            labelManager.getText(
                "ACC_BUTTON_BACK_TO",
                R.string.navigation_back_to
            )
            }"

        accessibilityTitle.visibility = if (isAccessibilityEnabled()) View.VISIBLE else View.GONE

    }

}
