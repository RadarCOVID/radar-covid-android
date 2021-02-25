/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkout.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutPresenter
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutView
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordPageCallback
import kotlinx.android.synthetic.main.fragment_checkout.*
import javax.inject.Inject

class CheckOutFragment : BaseFragment(), CheckOutView {

    companion object {

        fun newInstance() = CheckOutFragment()

    }

    @Inject
    lateinit var presenter: CheckOutPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_checkout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        buttonCheckOut.setSafeOnClickListener { presenter.onContinueButtonClick() }
        buttonCancel.setSafeOnClickListener { presenter.onCancelButtonClick() }
        buttonClose.setSafeOnClickListener { presenter.onCloseButtonClick() }
    }

    override fun performContinueButtonClick() {
        (activity as? VenueRecordPageCallback)?.onContinueButtonClick(VenueRecordPresenterImpl.CHECK_OUT_FRAGMENT)
    }

    override fun performCancelButtonClick() {
        (activity as? VenueRecordPageCallback)?.onCancelButtonClick()
    }

    override fun performCloseButtonClick() {
        (activity as? VenueRecordPageCallback)?.onBackButtonClick()
    }
}