/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.protocols.ErrorCapturedCodePresenter
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.protocols.ErrorCapturedCodeView
import es.gob.radarcovid.features.venuerecord.presenter.QRErrorState
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.view.VenueRecordActivity
import es.gob.radarcovid.features.venuerecord.view.VenueRecordPageCallback
import kotlinx.android.synthetic.main.fragment_venue_record_errorcode.*
import kotlinx.android.synthetic.main.fragment_venue_record_errorcode.errorButtonBack
import javax.inject.Inject

class ErrorCapturedCodeFragment : BaseFragment(), ErrorCapturedCodeView {

    companion object {

        fun newInstance() = ErrorCapturedCodeFragment()
    }

    @Inject
    lateinit var presenter: ErrorCapturedCodePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_venue_record_errorcode, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        errorButtonBack.contentDescription =
            "${labelManager.getText("ACC_VENUE_TITLE", R.string.title_home)} ${
                labelManager.getText(
                    "ACC_BUTTON_BACK_TO",
                    R.string.navigation_back_to
                )
            }"

        buttonScanQR.setSafeOnClickListener { presenter.onContinueButtonClick() }
        buttonClose.setSafeOnClickListener { presenter.onCancelButtonClick() }
    }

    override fun onResume() {
        super.onResume()
        setErrorMessage()
    }

    override fun performContinueButtonClick() {
        (activity as? VenueRecordPageCallback)?.onContinueButtonClick(VenueRecordPresenterImpl.ERROR_CAPTURED_CODE_FRAGMENT)
    }

    override fun performCancelButtonClick() {
        (activity as? VenueRecordPageCallback)?.onBackButtonClick()
    }

    private fun setErrorMessage() {
        val error = (activity as VenueRecordActivity).errorState
        textViewError.text =
            when (error) {
                QRErrorState.QR_CODE_NOT_VALID_ANYMORE -> labelManager.getText(
                    "QR_OUTDATED_ERROR",
                    R.string.venue_record_error_not_valid_anymore
                )
                QRErrorState.QR_CODE_NOT_YET_VALID -> labelManager.getText(
                    "QR_NOT_VALID_YET_ERROR",
                    R.string.venue_record_error_not_valid_yet
                )
                else -> labelManager.getText(
                    "VENUE_RECORD_ERROR_CODE_PARAGRAPH_1",
                    R.string.venue_record_error_code_desc
                )
            }
    }

}