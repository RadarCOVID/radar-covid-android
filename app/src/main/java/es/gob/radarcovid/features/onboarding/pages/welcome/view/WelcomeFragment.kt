/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.onboarding.pages.welcome.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.databinding.FragmentWelcomeBinding
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomePresenter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeView
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import javax.inject.Inject

class WelcomeFragment : BaseFragment(), WelcomeView {

    companion object {

        fun newInstance() = WelcomeFragment()

    }

    @Inject
    lateinit var presenter: WelcomePresenter

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.buttonContinue.setOnClickListener { presenter.onContinueButtonClick() }
    }

    override fun restorePreviousLanguage() {
        (childFragmentManager
            .findFragmentById(R.id.fragmentLocaleSelection) as LocaleSelectionFragment)
            .restoreLocaleSettings()
    }

    override fun performContinueButtonClick() {
        (activity as? OnboardingPageCallback)?.onContinueButtonClick(0)
    }

    override fun finish() {
        activity?.finish()
    }

}