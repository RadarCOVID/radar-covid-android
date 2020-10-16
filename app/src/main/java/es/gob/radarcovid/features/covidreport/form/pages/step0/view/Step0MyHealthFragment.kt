/*
 * Copyright (c) 2020 Gobierno de España
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 *  SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.covidreport.form.pages.step0.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthPresenter
import es.gob.radarcovid.features.covidreport.form.pages.step0.protocols.Step0MyHealthView
import es.gob.radarcovid.features.covidreport.form.view.CovidReportCallback
import kotlinx.android.synthetic.main.fragment_step0_my_health.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
import javax.inject.Inject

class Step0MyHealthFragment : BaseFragment(), Step0MyHealthView {

    companion object {

        fun newInstance() = Step0MyHealthFragment()

    }

    @Inject
    lateinit var presenter: Step0MyHealthPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step0_my_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        imageButtonBack.contentDescription =
            "${labelManager.getText("ACC_HOME_TITLE", R.string.title_home)} ${
                labelManager.getText(
                    "ACC_BUTTON_BACK_TO",
                    R.string.navigation_back_to
                )
            }"

        buttonContinue.setOnClickListener { presenter.onContinueButtonClick() }

        imageButtonBack.setOnClickListener { presenter.onBackButtonClick() }
    }

    override fun performContinueButtonClick() {
        (activity as? CovidReportCallback)?.onContinueButtonClick(0)
    }

    override fun finish() {
        (activity as? CovidReportCallback)?.onFinishButtonClick()
    }

}