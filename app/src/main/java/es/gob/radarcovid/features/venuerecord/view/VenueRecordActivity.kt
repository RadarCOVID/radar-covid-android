/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venuerecord.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.view.CommonDialog
import es.gob.radarcovid.features.qrcodescan.view.QRScanActivity
import es.gob.radarcovid.features.venuerecord.pages.confirmrecord.view.ConfirmRecordFragment
import es.gob.radarcovid.features.venuerecord.pages.checkout.view.CheckOutFragment
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.view.ErrorCapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.checkin.view.CheckInFragment
import es.gob.radarcovid.features.venuerecord.pages.checkout.presenter.VenueTimeOut
import es.gob.radarcovid.features.venuerecord.pages.recordsuccess.view.RecordSuccessFragment
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import es.gob.radarcovid.features.worker.VenueRecordWorker
import kotlinx.android.synthetic.main.activity_venue_record.*
import javax.inject.Inject

class VenueRecordActivity : BaseBackNavigationActivity(), VenueRecordView, VenueRecordPageCallback {

    companion object {

        private const val LAUNCH_QR_SCAN = 1

        fun open(context: Context) {
            context.startActivity(Intent(context, VenueRecordActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: VenueRecordPresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_QR_SCAN) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val result = data?.getStringExtra(QRScanActivity.EXTRA_QR_RESULT)
                    if (result != null) {
                        presenter.onOkScan(result)
                    }
                }

                Activity.RESULT_CANCELED -> {
                    presenter.onBackPressed(-1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_record)
        initViews()
        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(viewPager.currentItem)
    }

    override fun startQRScan() {
        startActivityForResult(
            Intent(this, QRScanActivity::class.java),
            LAUNCH_QR_SCAN
        )
    }

    override fun showFragment(position: Int) {
        viewPager.setCurrentItem(position, false)
    }

    fun getCurrentVenueName(): String =
        presenter.getCurrentVenueName()

    private fun initViews() {
        viewPager.adapter = VenueRecordAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 5
    }

    private class VenueRecordAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val totalPages = 5

        override fun getItemCount(): Int = totalPages

        override fun createFragment(position: Int): Fragment =
            when (position) {
                VenueRecordPresenterImpl.ERROR_CAPTURED_CODE_FRAGMENT -> ErrorCapturedCodeFragment.newInstance()
                VenueRecordPresenterImpl.CONFIRM_RECORD_FRAGMENT -> ConfirmRecordFragment.newInstance()
                VenueRecordPresenterImpl.CHECK_IN_FRAGMENT -> CheckInFragment.newInstance()
                VenueRecordPresenterImpl.CHECK_OUT_FRAGMENT -> CheckOutFragment.newInstance()
                VenueRecordPresenterImpl.RECORD_SUCCESS_FRAGMENT -> RecordSuccessFragment.newInstance()
                else -> ConfirmRecordFragment.newInstance()
            }
    }

    override fun onBackPressed() {
        presenter.onBackPressed(viewPager.currentItem)
    }

    override fun onContinueButtonClick(pageIndex: Int) {
        presenter.onContinueButtonClick(pageIndex)
    }

    override fun onCancelButtonClick() {
        CommonDialog.Builder(this)
            .setTitle(
                labelManager.getText(
                    "VENUE_RECORD_CANCEL_TITLE",
                    R.string.venue_record_cancel_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "VENUE_RECORD_CANCEL_TEXT",
                    R.string.venue_record_cancel_text
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "VENUE_RECORD_CANCEL_CONTINUE",
                    R.string.venue_record_cancel_continue
                ).toString()
            ) {
                it.dismiss()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.cancel
                ).toString()
            ) {
                it.dismiss()
                presenter.cancelRecord()
                this.finish()
            }
            .setCloseButton() {
                it.dismiss()
            }
            .build()
            .show()
    }

    override fun onBackButtonClick() {
        onBackPressed()
    }

    override fun exit() {
        this.finish()
    }

    override fun setVenueTimeOut(venueTimeOut: VenueTimeOut) =
        presenter.setVenueTimeOut(venueTimeOut)

    override fun startVenueRecordWorker() {
        VenueRecordWorker.set(this, 1)
    }

    override fun cancelVenueRecordWorker() {
        VenueRecordWorker.cancel(this)
    }

}