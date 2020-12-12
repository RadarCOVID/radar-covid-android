/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step2.protocols.Step2MyHealthView
import es.gob.radarcovid.features.covidreport.form.view.CovidReportCallback
import kotlinx.android.synthetic.main.fragment_step2_my_health.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
import org.dpppt.android.sdk.DP3T
import java.util.*
import javax.inject.Inject

class Step2MyHealthFragment : BaseFragment(), Step2MyHealthView {

    companion object {
        fun newInstance() = Step2MyHealthFragment()
    }

    @Inject
    lateinit var presenter: Step2MyHealthPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step2_my_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        imageButtonBack.contentDescription =
            "${labelManager.getText("MY_HEALTH_TITLE_STEP1", R.string.covid_report_title_code)} ${
                labelManager.getText(
                    "ACC_BUTTON_BACK_TO",
                    R.string.navigation_back_to
                )
            }"

        buttonSend.setOnClickListener {
            presenter.onSendButtonClick()
        }

        imageButtonBack.setOnClickListener {
            (activity as? CovidReportCallback)?.onContinueButtonClick(
                1
            )
        }

        buttonCancel.setOnClickListener { presenter.onCancel() }

        stepsProgress.apply {
            visibility = View.VISIBLE
            setSteps(2, 2)
        }
    }

    override fun showExitConfirmationDialog() {
        (activity as? CovidReportCallback)?.onFinishButtonClick()
    }

    override fun hideLoadingWithNetworkError() {
        hideLoadingWithError(
            labelManager.getText(
                "ALERT_NETWORK_ERROR_TITLE",
                R.string.network_warning_dialog_title
            ).toString(),
            labelManager.getText(
                "ALERT_POSITIVE_REPORT_NETWORK_ERROR_MESSAGE",
                R.string.network_warning_dialog_message
            ).toString(),
            labelManager.getText(
                "ALERT_RETRY_BUTTON",
                R.string.network_warning_dialog_button
            ).toString()
        ) {
            presenter.onRetryButtonClick()
        }
    }

    override fun hideLoadingWithErrorOnReport() {
        hideLoadingWithError(
            Exception(
                labelManager.getText(
                    "ALERT_MY_HEALTH_CODE_ERROR_CONTENT",
                    R.string.covid_report_error
                ).toString()
            )
        )
    }

    override fun getReportCode(): String? =
        (activity as? CovidReportCallback)?.getReportCodeFromStep1()

    override fun getDateSelected(): Date? = (activity as? CovidReportCallback)?.getDateFromStep1()

    override fun getSharedDiagnostic(): Int {
        val radioButton = radioGroup.checkedRadioButtonId

        return radioGroup.indexOfChild(radioGroup.findViewById(radioButton))
    }

    override fun finish() {
        activity?.finish()
    }

    override fun performBackButtonClick() {
        (activity as? CovidReportCallback)?.onContinueButtonClick(1)
    }

    override fun onCancelClick() {
        (activity as? CovidReportCallback)?.onFinishButtonClick()
    }

}