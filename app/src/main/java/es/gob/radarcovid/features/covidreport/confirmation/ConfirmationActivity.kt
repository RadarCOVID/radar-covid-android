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
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.base.utils.NavigationUtils
import es.gob.radarcovid.databinding.ActivityConfirmationBinding
import javax.inject.Inject

class ConfirmationActivity : BaseActivity() {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ConfirmationActivity::class.java))
        }

    }

    @Inject
    lateinit var navigationUtils: NavigationUtils

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    fun onBackArrowClick(view: View) {
        onBackPressed()
    }

    private fun initViews() {
        binding.textViewConfirmationMoreInfo.setOnClickListener {
            navigationUtils.navigateToBrowser(
                this,
                labelManager.getText(
                    "MY_HEALTH_REPORTED_MORE_INFO_URL",
                    R.string.confirmation_more_info_url
                ).toString()
            )
        }
        binding.buttonMoreInfo.setOnClickListener {
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
