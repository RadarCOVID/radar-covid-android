/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.stats.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.view.adapter.ListAdapter
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_stats_countries.*
import javax.inject.Inject

class StatsCountriesDialog(context: Context, countries: List<String>) : LayoutContainer {

    private val dialog: Dialog

    override val containerView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_stats_countries, null)

    @Inject
    lateinit var labelManager: LabelManager

    init {
        initializeInjector(context)

        dialog = AlertDialog.Builder(context)
            .setView(containerView)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        buttonClose.setOnClickListener { dialog.dismiss() }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ListAdapter(countries)

        scrollViewContent.smoothScrollTo(0, 0);

    }

    fun show() {
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun initializeInjector(context: Context) {
        (context.applicationContext as? HasAndroidInjector)?.androidInjector()?.inject(this)
    }
}