/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venue.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.venue.protocols.VenuePresenter
import es.gob.radarcovid.features.venue.protocols.VenueView
import kotlinx.android.synthetic.main.fragment_helpline.*
import kotlinx.android.synthetic.main.fragment_venue_home.*
import javax.inject.Inject

class VenueFragment : BaseFragment(), VenueView {

    companion object {
        fun newInstance() = VenueFragment()
    }

    @Inject
    lateinit var presenter: VenuePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venue_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        setAccessibilityFocus()
    }

    private fun initViews() {
        buttonVenueRecord.setSafeOnClickListener { presenter.onRecordButtonClick() }
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