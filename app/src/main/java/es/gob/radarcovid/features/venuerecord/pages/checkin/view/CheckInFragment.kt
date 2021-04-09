/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.checkin.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.venuerecord.pages.checkin.protocols.CheckInPresenter
import es.gob.radarcovid.features.venuerecord.pages.checkin.protocols.CheckInView
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordPageCallback
import es.gob.radarcovid.models.domain.VenueRecord
import kotlinx.android.synthetic.main.fragment_venue_record_checkin.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CheckInFragment : BaseFragment(), CheckInView {

    companion object {

        fun newInstance() = CheckInFragment()

    }

    @Inject
    lateinit var presenter: CheckInPresenter

    lateinit var mainHandler: Handler

    private val updateTextTask = object : Runnable {
        override fun run() {
            setTime(presenter.getDateIn())
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_venue_record_checkin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainHandler = Handler(Looper.getMainLooper())
        initViews()
    }

    override fun setVenueData(currentVenue: VenueRecord?) {
        textViewVenueName.text = currentVenue?.name
    }

    override fun onResume() {
        super.onResume()
        presenter.viewReady()
        mainHandler.post(updateTextTask)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    private fun initViews() {
        checkinButtonBack.contentDescription =
            "${labelManager.getText("ACC_HOME_TITLE", R.string.title_home)} ${
                labelManager.getText(
                    "ACC_BUTTON_BACK_TO",
                    R.string.navigation_back_to
                )
            }"

        buttonConfirm.setSafeOnClickListener { presenter.onContinueButtonClick() }
        buttonCancel.setSafeOnClickListener { presenter.onCancelButtonClick() }
        checkinButtonBack.setSafeOnClickListener { presenter.onExitButtonClick() }
    }

    override fun startTimer(date: Date) {
        mainHandler.post(updateTextTask)
    }

    fun setTime(date: Date) {
        val millisElapsed = System.currentTimeMillis() - date.time
        val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
        val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
        val minutesElapsed =
            TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)
        val secondsElapsed =
            TimeUnit.MILLISECONDS.toSeconds(millisElapsed) - (hoursElapsed * 60 * 60) - (minutesElapsed * 60)
        textViewTime.text =
            String.format("%02d:%02d:%02d", hoursElapsed, minutesElapsed, secondsElapsed)
    }

    override fun setVenueInfo(currentVenue: VenueRecord) {
        textViewVenueName.text = currentVenue.name
    }

    override fun performContinueButtonClick() {
        (activity as? VenueRecordPageCallback)?.onContinueButtonClick(VenueRecordPresenterImpl.CHECK_IN_FRAGMENT)
    }

    override fun performCancelButtonClick() {
        (activity as? VenueRecordPageCallback)?.onCancelButtonClick()
    }

    override fun performExitButtonClick() {
        (activity as? VenueRecordPageCallback)?.onBackButtonClick()
    }

}