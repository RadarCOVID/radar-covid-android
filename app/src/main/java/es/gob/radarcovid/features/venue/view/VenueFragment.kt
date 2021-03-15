/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.venue.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.venue.protocols.VenuePresenter
import es.gob.radarcovid.features.venue.protocols.VenueView
import kotlinx.android.synthetic.main.fragment_helpline.*
import kotlinx.android.synthetic.main.fragment_venue_home.*
import javax.inject.Inject

class VenueFragment : BaseFragment(), VenueView {

    companion object {
        fun newInstance() = VenueFragment()
    }

    @Inject
    lateinit var presenter: VenuePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venue_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        setAccessibilityFocus()
    }

    private fun initViews() {
        buttonVenueRecord.setSafeOnClickListener { presenter.onRecordButtonClick() }
        buttonPlaces.setSafeOnClickListener { presenter.onButtonPlacesClick() }
    }

    private fun setAccessibilityFocus() {
        if (isAccessibilityEnabled())
            textViewTitle.postDelayed({
                if (textViewTitle != null) {
                    textViewTitle.isFocusable = true
                    textViewTitle.requestFocus()
                    textViewTitle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
                }
            }, 3000)
    }

    override fun authenticateAndShowPlaces() {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(requireActivity(), executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    presenter.showPlaces()
                }
            })

        if (BiometricManager.from(requireContext())
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            val title = labelManager.getText(
                "VENUE_BIOMETRIC_PROMPT_TITLE",
                R.string.venue_biometric_prompt_tittle
            )
            val message = labelManager.getText(
                "VENUE_BIOMETRIC_PROMPT_MESSAGE",
                R.string.venue_biometric_prompt_message
            )

            biometricPrompt.authenticate(
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    //.setSubtitle(message)
                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    .build()
            )
        } else {
            presenter.showPlaces()
            // Prompts the user to create credentials that your app accepts.
//            val enrollIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                Intent(android.provider.Settings.ACTION_FINGERPRINT_ENROLL)
//            } else {
//                Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS)
//            }
//            startActivityForResult(enrollIntent, 0)
        }
    }
}