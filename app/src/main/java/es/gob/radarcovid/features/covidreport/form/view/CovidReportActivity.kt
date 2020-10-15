/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.covidreport.form.pages.step0.view.Step0MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step1.view.Step1MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.pages.step2.view.Step2MyHealthFragment
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportPresenter
import es.gob.radarcovid.features.covidreport.form.protocols.CovidReportView
import kotlinx.android.synthetic.main.activity_covid_report.viewPager
import javax.inject.Inject

class CovidReportActivity : BaseBackNavigationActivity(), CovidReportView, CovidReportCallback {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, CovidReportActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: CovidReportPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_report)

        initViews()
        presenter.viewReady()
    }

    override fun onPause() {
        super.onPause()
        hideKeyBoard()
    }

    private fun initViews() {
        viewPager.adapter = CovidReportAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 4
    }

    override fun onBackPressed() {
        presenter.onBackButtonPressed(viewPager.currentItem == 0)
    }

    override fun onBackArrowClick(view: View) {
        hideKeyBoard()
    }

    override fun showPreviousPage() {
        viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    override fun showNextPage() {
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }


    override fun showExitConfirmationDialog() {
        CMDialog.Builder(this)
            .setTitle(
                labelManager.getText(
                    "ALERT_MY_HEALTH_SEND_TITLE",
                    R.string.covid_report_abort_warning_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "ALERT_MY_HEALTH_SEND_CONTENT",
                    R.string.covid_report_abort_warning_message
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_CANCEL_SEND_BUTTON",
                    R.string.covid_report_abort_send_warning_button
                ).toString()
            ) {
                it.dismiss()
                presenter.onExitConfirmed()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ACC_BUTTON_CLOSE",
                    R.string.dialog_close_button_description
                ).toString()
            ) {
                it.dismiss()
            }
            .build()
            .show()
    }


    private class CovidReportAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val totalPages = 3

        override fun getItemCount(): Int = totalPages

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> Step0MyHealthFragment.newInstance()
                1 -> Step1MyHealthFragment.newInstance()
                2 -> Step2MyHealthFragment.newInstance()
                else -> Step0MyHealthFragment.newInstance()
            }
    }

    override fun onContinueButtonClick(pageIndex: Int) {
        if (viewPager.currentItem == pageIndex)
            presenter.onContinueButtonClick()
    }

    override fun onFinishButtonClick() {
        presenter.onFinishButtonClick()
    }

    override fun hideKeyboard() {
        hideKeyBoard()
    }

}