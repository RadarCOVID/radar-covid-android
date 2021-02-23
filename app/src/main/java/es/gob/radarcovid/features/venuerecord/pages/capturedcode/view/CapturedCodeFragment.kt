/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.capturedcode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import es.gob.radarcovid.features.venuerecord.pages.capturedcode.protocols.CapturedCodePresenter
import es.gob.radarcovid.features.venuerecord.pages.capturedcode.protocols.CapturedCodeView
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordPageCallback
import kotlinx.android.synthetic.main.fragment_captured_code.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

class CapturedCodeFragment : BaseFragment(), CapturedCodeView {

    companion object {

        fun newInstance() = CapturedCodeFragment()

    }

    @Inject
    lateinit var presenter: CapturedCodePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_captured_code, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        buttonCheckIn.setOnClickListener { presenter.onContinueButtonClick() }
    }

    override fun performContinueButtonClick() {
        (activity as? VenueRecordPageCallback)?.onContinueButtonClick(VenueRecordPresenterImpl.CAPTURED_CODE_FRAGMENT)
    }

    override fun finish() {
        activity?.finish()
    }
}