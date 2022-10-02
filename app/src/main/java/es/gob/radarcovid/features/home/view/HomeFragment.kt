/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.home.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.common.extensions.parseHtml
import es.gob.radarcovid.common.extensions.setAccessibilityAction
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.common.view.LegalTermsDialog
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeView {

    companion object {

        private const val REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS = 1

        private const val ARG_ACTIVATE_RADAR = "arg_activate_radar"
        private const val MANUAL_NAVIGATION = "arg_manual_navigation"
        private const val BACK_FROM_QR = "back_from_qr"

        fun newInstance(activateRadar: Boolean, manualNavigation: Boolean, backFromQr: Boolean) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_ACTIVATE_RADAR, activateRadar)
                    putBoolean(MANUAL_NAVIGATION, manualNavigation)
                    putBoolean(BACK_FROM_QR, backFromQr)
                }
            }

    }

    @Inject
    lateinit var presenter: HomePresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS && resultCode == Activity.RESULT_OK)
            presenter.onBatteryOptimizationsIgnored()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady(arguments?.getBoolean(ARG_ACTIVATE_RADAR) ?: false)
    }

    override fun onResume() {
        super.onResume()
        updateViewsForAccessibility(
            arguments?.getBoolean(MANUAL_NAVIGATION) ?: false,
            arguments?.getBoolean(BACK_FROM_QR) ?: false
        )
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun initViews() {
        switchRadar.setOnClickListener {
            if (!switchRadar.isChecked) { // The status is already change when onclick is executed
                switchRadar.isChecked = true
                switchRadar.jumpDrawablesToCurrentState()
                showDialogDisableRadarWarning()
            } else {
                switchRadar.isChecked = false
                switchRadar.jumpDrawablesToCurrentState()
                presenter.onSwitchRadarClick(false)
            }
        }
        switchRadar.setOnTouchListener { _, event ->
            event.actionMasked == MotionEvent.ACTION_MOVE
        }

        wrapperExposure.setSafeOnClickListener { presenter.onExposureBlockClick() }
        textViewExpositionTitle.setSafeOnClickListener { presenter.onExposureBlockClick() }

        wrapperVenueExposure.setSafeOnClickListener { presenter.onVenueExposureBlockClick() }
        textViewVenueExpositionTitle.setSafeOnClickListener { presenter.onVenueExposureBlockClick() }

        buttonCovidReport.setSafeOnClickListener { presenter.onReportButtonClick() }

        buttonShare.setSafeOnClickListener { presenter.onButtonShareClick() }
    }

    override fun showUpdateLegalTermsDialog() {
        LegalTermsDialog(requireContext()).show {
            presenter.legalTermsAccepted()
        }
    }

    override fun showInitializationCheckAnimation() {
        imageViewLogo.alpha = 0f
        imageViewInitializationCheck.alpha = 1f
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(imageViewLogo, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(imageViewInitializationCheck, View.ALPHA, 1f, 0f)
            )
            duration = 2000 //set duration for animations
            startDelay = 2000
        }.start()
    }

    override fun showVenueExposureBlock(daysToHeal: Int, hideExposureBlock: Boolean) {
        wrapperVenueExposure.visibility = View.VISIBLE
        wrapperExposure.visibility = if (hideExposureBlock) View.GONE else View.VISIBLE
        wrapperVenueExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        textViewVenueExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_HIGH", R.string.exposition_block_high_title)
        textViewVenueExpositionDescription.text =
            labelManager.getFormattedText(
                "HOME_VENUE_EXPOSITION_MESSAGE_HIGH",
                labelManager.getContactPhone()
            ).default(getString(R.string.venue_exposure_block_high_description)).parseHtml()

        if (daysToHeal >= 0) {
            val labelId =
                if (daysToHeal == 1) "HOME_EXPOSITION_COUNT_ONE_DAY" else "HOME_EXPOSITION_COUNT_ANYMORE"
            textViewVenueExpositionCount.text =
                labelManager.getFormattedText(labelId, daysToHeal.toString())
                    .default(getString(R.string.exposition_block_high_count)).parseHtml()
            textViewVenueExpositionCount.visibility = View.VISIBLE
        } else {
            textViewVenueExpositionCount.visibility = View.INVISIBLE
        }
    }

    override fun showExposureBlockLow() {
        wrapperExposure.visibility = View.VISIBLE
        wrapperVenueExposure.visibility = View.GONE
        imageViewRisk.visibility = View.GONE
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_expure_low)
        textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_LOW", R.string.exposition_block_low_title)
        textViewExpositionDescription.text =
            labelManager.getText(
                "HOME_EXPOSITION_MESSAGE_LOW",
                R.string.exposition_block_low_description
            )
        textViewMoreInfo.visibility = View.GONE
        textViewExpositionCount.visibility = View.INVISIBLE
    }

    override fun showExposureBlockHigh(daysToHeal: Int, hideVenueExposureBlock: Boolean) {
        wrapperExposure.visibility = View.VISIBLE
        wrapperVenueExposure.visibility = if (hideVenueExposureBlock) View.GONE else View.VISIBLE
        imageViewRisk.visibility = View.VISIBLE
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_HIGH", R.string.exposition_block_high_title)
        textViewExpositionDescription.text =
            labelManager.getFormattedText(
                "HOME_EXPOSITION_MESSAGE_HIGH",
                labelManager.getContactPhone()
            ).default(getString(R.string.exposition_block_high_description)).parseHtml()
        textViewMoreInfo.visibility = View.GONE

        if (daysToHeal >= 0) {
            val labelId =
                if (daysToHeal == 1) "HOME_EXPOSITION_COUNT_ONE_DAY" else "HOME_EXPOSITION_COUNT_ANYMORE"
            textViewExpositionCount.text =
                labelManager.getFormattedText(labelId, daysToHeal.toString())
                    .default(getString(R.string.exposition_block_high_count)).parseHtml()
            textViewExpositionCount.visibility = View.VISIBLE
        } else {
            textViewExpositionCount.visibility = View.INVISIBLE
        }

    }

    override fun showExposureBlockInfected() {
        wrapperExposure.visibility = View.VISIBLE
        wrapperVenueExposure.visibility = View.GONE
        imageViewRisk.visibility = View.GONE
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_infected)
        textViewExpositionTitle.text = labelManager.getText(
            "HOME_EXPOSITION_TITLE_POSITIVE",
            R.string.exposition_block_infected_title
        )
        textViewExpositionDescription.text =
            labelManager.getText(
                "HOME_EXPOSITION_MESSAGE_INFECTED",
                R.string.exposition_block_infected_description
            )
        textViewMoreInfo.visibility = View.VISIBLE
        textViewExpositionCount.visibility = View.INVISIBLE
    }

    override fun showBackgroundEnabled(enabled: Boolean) {
        if (enabled) {
            if (imageViewLogo.colorFilter != null) {
                imageViewLogoBackground.clearColorFilter()
                imageViewLogo.clearColorFilter()
            }
        } else {
            ColorMatrixColorFilter(ColorMatrix().apply {
                setSaturation(0f)
            }).let {
                if (imageViewLogo.colorFilter == null) {
                    imageViewLogoBackground.colorFilter = it
                    imageViewLogo.colorFilter = it
                }
            }
        }
    }

    override fun showReportButton() {
        buttonCovidReport.visibility = View.VISIBLE
    }

    override fun hideReportButton() {
        buttonCovidReport.visibility = View.GONE
    }

    override fun setFakeExposureButton() {
        imageViewInitializationCheck.setOnLongClickListener {
            presenter.onFakeExposureButtonClick()
            true
        }
        imageViewLogo.setOnLongClickListener {
            presenter.onFakeExposureButtonClick()
            true
        }
        imageViewLogo.contentDescription = getString(R.string.home_exposure_simulation_description)

        textViewVersion.text = context?.getString(
            R.string.home_exposure_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE.toString()
        )
        textViewVersion.visibility = View.VISIBLE
        textViewVersion.setOnLongClickListener {
            presenter.onFakeShowExposureHealedDialogClick()
            true
        }
    }

    override fun setRadarBlockChecked(checked: Boolean) {
        if (switchRadar.isChecked != checked) {
            switchRadar.isChecked = checked
            showRadarBlockChecked(checked)
        }
    }

    override fun setRadarBlockEnabled(enabled: Boolean) {
        if (enabled) {
            switchRadar.visibility = View.VISIBLE
        } else {
            switchRadar.visibility = View.GONE
            textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_INACTIVE",
                R.string.radar_block_not_checked_title
            )
            textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_MESSAGE_DISABLED",
                R.string.radar_block_disabled_description
            )
            textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(requireContext(), R.font.roboto_light),
                Typeface.NORMAL
            )
        }

    }

    override fun areBatteryOptimizationsIgnored(): Boolean = false

    @SuppressLint("BatteryLife")
    override fun requestIgnoreBatteryOptimizations() {

    }

    override fun showUnableToReportCovidDialog() {
        CMDialog.Builder(requireContext())
            .setMessage(
                labelManager.getText(
                    "ALERT_RADAR_REQUIRED_TO_REPORT",
                    R.string.covid_report_warning
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_ACCEPT_BUTTON",
                    R.string.accept
                ).toString()
            ) { it.dismiss() }
            .build()
            .show()
    }

    override fun showExposureHealedDialog() {
        ExposureHealedDialog(requireContext()).show()
    }

    override fun showShareDialog() {
        ShareDialog(requireContext()) {
            presenter.doShareApp(
                labelManager.getText(
                    "SHARE_TEXT",
                    getString(R.string.share_app_text)
                ).toString()
            )
        }.show()
    }

    private fun showDialogDisableRadarWarning() {
        CMDialog.Builder(requireContext())
            .setTitle(
                labelManager.getText(
                    "ALERT_HOME_RADAR_TITLE",
                    R.string.radar_warning_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "ALERT_HOME_RADAR_CONTENT",
                    R.string.radar_warning_message
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_HOME_RADAR_CANCEL_BUTTON",
                    R.string.radar_warning_button_positive
                ).toString()
            ) {
                it.dismiss()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_HOME_RADAR_OK_BUTTON",
                    R.string.radar_warning_button_negative
                ).toString()
            ) {
                it.dismiss()
                presenter.onSwitchRadarClick(true)
            }
            .build()
            .show()
    }

    private fun showRadarBlockChecked(isChecked: Boolean) {
        if (isChecked) {
            textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_ACTIVE",
                R.string.radar_block_checked_title
            )
            textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_CONTENT_ACTIVE",
                R.string.radar_block_checked_description
            )
            textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(requireContext(), R.font.roboto_light),
                Typeface.NORMAL
            )
        } else {
            textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_INACTIVE",
                R.string.radar_block_not_checked_title
            )
            textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_CONTENT_INACTIVE",
                R.string.radar_block_not_checked_description
            )
            textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red_00
                )
            )
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(requireContext(), R.font.roboto_bold),
                Typeface.NORMAL
            )
        }
    }

    private fun updateViewsForAccessibility(manualNavigation: Boolean, backFromQr: Boolean) {
        if (isAccessibilityEnabled()) {
            wrapperExposure.isClickable = false
            textViewExpositionTitle.isClickable = true
        } else {
            wrapperExposure.isClickable = true
            textViewExpositionTitle.isClickable = false
        }
        if (manualNavigation) {
            if (isAccessibilityEnabled()) {
                val delay = if (backFromQr) 1000L else 3000L
                textViewTitle.postDelayed({
                    if (textViewTitle != null) {
                        textViewTitle.isFocusable = true
                        textViewTitle.requestFocus()
                        textViewTitle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
                    }
                }, delay)
            }
        }
    }

    override fun updateContentDescriptionRadar(enabled: Boolean) {
        val action = if (enabled)
            labelManager.getText(
                "ALERT_HOME_RADAR_OK_BUTTON",
                R.string.radar_warning_button_negative
            ).toString()
        else labelManager.getText(
            "ALERT_HOME_COVID_NOTIFICATION_OK_BUTTON",
            R.string.radar_warning_button_activate
        ).toString()
        switchRadar.setAccessibilityAction(action)
    }

}