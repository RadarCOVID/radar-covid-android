/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.settings.protocols.SettingsPresenter
import es.gob.radarcovid.features.settings.protocols.SettingsView
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
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

    private fun initViews() {
        buttonInformation.setOnClickListener { presenter.onInformationClick() }
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