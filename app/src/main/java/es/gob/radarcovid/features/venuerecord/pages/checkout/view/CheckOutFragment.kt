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
import android.widget.RadioGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.*
import es.gob.radarcovid.features.venuerecord.pages.checkout.presenter.VenueTimeOut
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutPresenter
import es.gob.radarcovid.features.venuerecord.pages.checkout.protocols.CheckOutView
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordPageCallback
import es.gob.radarcovid.models.domain.VenueRecord
import kotlinx.android.synthetic.main.fragment_step1_my_health.*
import kotlinx.android.synthetic.main.fragment_venue_record_checkout.*
import kotlinx.android.synthetic.main.fragment_venue_record_checkout.buttonCancel
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
    ): View? = inflater.inflate(R.layout.fragment_venue_record_checkout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        buttonCheckOut.isEnabled = segmentedControlTime.checkedRadioButtonId != -1
        presenter.viewReady()
    }

    private fun initViews() {
        buttonCheckOut.setSafeOnClickListener { presenter.onContinueButtonClick() }
        buttonCancel.setSafeOnClickListener { presenter.onCancelButtonClick() }
        buttonClose.setSafeOnClickListener { presenter.onCloseButtonClick() }
        segmentedControlTime.setOnCheckedChangeListener { r,i -> presenter.onControlTimeClick() }
    }

    override fun setButtonContinueEnabled(enabled: Boolean) {
        buttonCheckOut.isEnabled = enabled
    }

    override fun setVenueData(currentVenue: VenueRecord?) {
        currentVenue?.let {
            textViewVenueName.text = it.name
            if (it.dateIn.isToday()) {
                textViewDay.text = labelManager.getFormattedTextHtml(
                    "VENUE_RECORD_CHECKOUT_TODAY",
                    getString(R.string.venue_checkout_today),
                    String.format("%s %s", it.dateIn.getDayString(), it.dateIn.geMonthNameDefault())
                )
            } else {
                textViewDay.text = labelManager.getFormattedTextHtml(
                    "VENUE_RECORD_CHECKOUT_YESTERDAY",
                    getString(R.string.venue_checkout_yesterday),
                    String.format("%s %s", it.dateIn.getDayString(), it.dateIn.geMonthNameDefault())
                )
            }

            textViewTime.text = labelManager.getFormattedTextHtml(
                "VENUE_RECORD_CHECKOUT_HOUR",
                getString(R.string.venue_checkout_hour),
                it.dateIn.getHourString()
            )
        }
    }

    private fun getTimeOut(): VenueTimeOut =
        when (segmentedControlTime.checkedRadioButtonId) {
            R.id.timeOptionNow -> VenueTimeOut.NOW
            R.id.timeOption30 -> VenueTimeOut.OPT_30
            R.id.timeOption1 -> VenueTimeOut.OPT_1
            R.id.timeOption2 -> VenueTimeOut.OPT_2
            R.id.timeOption4 -> VenueTimeOut.OPT_4
            else -> VenueTimeOut.OPT_5
        }

    override fun performContinueButtonClick() {
        (activity as? VenueRecordPageCallback)?.setVenueTimeOut(getTimeOut())
        (activity as? VenueRecordPageCallback)?.onContinueButtonClick(VenueRecordPresenterImpl.CHECK_OUT_FRAGMENT)
    }

    override fun performCancelButtonClick() {
        (activity as? VenueRecordPageCallback)?.onCancelButtonClick()
    }

    override fun performCloseButtonClick() {
        (activity as? VenueRecordPageCallback)?.onBackButtonClick()
    }
}