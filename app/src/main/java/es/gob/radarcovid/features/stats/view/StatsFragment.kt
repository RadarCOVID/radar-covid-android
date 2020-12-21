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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.features.stats.protocols.StatsPresenter
import es.gob.radarcovid.features.stats.protocols.StatsView
import kotlinx.android.synthetic.main.fragment_stats.*
import javax.inject.Inject

class StatsFragment : BaseFragment(), StatsView {

    companion object {
        fun newInstance() = StatsFragment()
    }

    @Inject
    lateinit var presenter: StatsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        setAccessibilityFocus()
    }

    override fun showCountriesDialog(countries: List<String>) {
        StatsCountriesDialog(context!!, countries).show()
    }

    override fun setDataView(
        countries: String,
        date: String,
        downloads: String,
        contagions: String
    ) {
        textViewCountries.text = countries
        textViewDownloads.text = downloads
        textViewPositive.text = contagions
        textViewUpdateData.text = labelManager.getFormattedTextHtml(
            "STATS_UPDATE",
            getString(R.string.stats_update_date),
            date
        )

    }

    private fun initViews() {
        textViewCountriesLink.setOnClickListener {
            presenter.onCountriesClick()
        }
    }

    private fun setAccessibilityFocus() {
        if (isAccessibilityEnabled())
            textViewTitle.postDelayed({
                if (textViewTitle != null) {
                    textViewTitle.isFocusable = true
                    textViewTitle.requestFocus()
                    textViewTitle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
                }
            }, 3000)
    }

}