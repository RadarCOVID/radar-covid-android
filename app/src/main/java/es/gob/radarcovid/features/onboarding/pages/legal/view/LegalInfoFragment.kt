/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.legal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoView
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import kotlinx.android.synthetic.main.fragment_legal_info.*
import javax.inject.Inject


class LegalInfoFragment : BaseFragment(), LegalInfoView {

    companion object {

        fun newInstance() = LegalInfoFragment()

    }

    @Inject
    lateinit var presenter: LegalInfoPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_legal_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        checkBoxTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            presenter.onLegalTermsCheckedChange(
                isChecked
            )
        }

        textViewPrivacyPolicy.setOnClickListener { presenter.onPrivacyPolicyButtonClick() }
        textViewUsageConditions.setOnClickListener { presenter.onConditionsButtonClick() }

        buttonAccept.setOnClickListener {
            (activity as? OnboardingPageCallback)?.onContinueButtonClick(
                1
            )
        }

    }

    override fun setLegalTermsChecked() {
        checkBoxTermsAndConditions.isChecked = true
    }

    override fun setContinueButtonEnabled(enabled: Boolean) {
        buttonAccept.isEnabled = enabled
    }

}
