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
import es.gob.radarcovid.features.onboarding.pages.ActivationPageFragment
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment
import es.gob.radarcovid.features.onboarding.pages.welcome.view.WelcomeFragment
import es.gob.radarcovid.features.qrcodescan.view.QRScanActivity
import es.gob.radarcovid.features.venuerecord.pages.capturedcode.view.CapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.view.ErrorCapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.view.RecordInitiatedFragment
import es.gob.radarcovid.features.venuerecord.presenter.VenueRecordPresenterImpl
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordPresenter
import es.gob.radarcovid.features.venuerecord.protocols.VenueRecordView
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.activity_venue_record.*
import kotlinx.android.synthetic.main.activity_venue_record.viewPager
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
                    presenter.onErrorScan()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_record)
        initViews()
        presenter.startScan()
    }

    override fun startQRScan() {
        startActivityForResult(
            Intent(this, QRScanActivity::class.java),
            LAUNCH_QR_SCAN
        )
    }

    override fun showFragment(position: Int) {
        viewPager.setCurrentItem(position, true)
    }

    private fun initViews() {
        viewPager.adapter = VenueRecordAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 4
    }

    private class VenueRecordAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val totalPages = 3

        override fun getItemCount(): Int = totalPages

        override fun createFragment(position: Int): Fragment =
            when (position) {
                VenueRecordPresenterImpl.ERROR_CAPTURED_CODE_FRAGMENT -> ErrorCapturedCodeFragment.newInstance()
                VenueRecordPresenterImpl.CAPTURED_CODE_FRAGMENT -> CapturedCodeFragment.newInstance()
                VenueRecordPresenterImpl.INITIATED_RECORD_FRAGMENT -> RecordInitiatedFragment.newInstance()
                else -> CapturedCodeFragment.newInstance()
            }
    }

    override fun onContinueButtonClick(pageIndex: Int) {
        when (pageIndex) {
            VenueRecordPresenterImpl.ERROR_CAPTURED_CODE_FRAGMENT -> {
                //Retry Scan
                presenter.startScan()
            }
            VenueRecordPresenterImpl.CAPTURED_CODE_FRAGMENT -> {
                //do check in
                presenter.doCheckIn()
            }
        }
    }

    override fun onCancelButtonClick() {
        TODO("Not yet implemented")
    }

    override fun onExitButtonClick() {
        this.finish()
    }

}