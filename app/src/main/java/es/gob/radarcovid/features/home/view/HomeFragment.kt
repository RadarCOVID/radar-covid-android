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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.common.extensions.parseHtml
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeView {

    companion object {

        private const val REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS = 1

        private const val ARG_ACTIVATE_RADAR = "arg_activate_radar"

        fun newInstance(activateRadar: Boolean) = HomeFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_ACTIVATE_RADAR, activateRadar)
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

        imageViewLogo.setOnLongClickListener {
            presenter.onBackgroundImageLongClick()
            true
        }
        imageViewInitializationCheck.setOnLongClickListener {
            presenter.onBackgroundImageLongClick()
            true
        }
        wrapperExposure.setOnClickListener { presenter.onExposureBlockClick() }
        textViewMoreInfo.setOnClickListener {
            presenter.onMoreInfoButtonClick(
                labelManager.getText(
                    "HOME_EXPOSITION_POSITIVE_MORE_INFO_URL",
                    R.string.exposition_block_infected_more_info_url
                ).toString()
            )
        }
        buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
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

    override fun showExposureBlockLow() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_expure_low)
        textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_LOW", R.string.exposition_block_low_title)
        textViewExpositionDescription.text =
            labelManager.getText(
                "HOME_EXPOSITION_MESSAGE_LOW",
                R.string.exposition_block_low_description
            )
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.green))
        textViewMoreInfo.visibility = View.GONE
    }

    override fun showExposureBlockHigh() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_HIGH", R.string.exposition_block_high_title)
        textViewExpositionDescription.text =
            labelManager.getFormattedText(
                "HOME_EXPOSITION_MESSAGE_HIGH",
                labelManager.getContactPhone()
            ).default(getString(R.string.exposition_block_high_description)).parseHtml()
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
        textViewMoreInfo.visibility = View.GONE
    }

    override fun showExposureBlockInfected() {
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
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
        textViewMoreInfo.visibility = View.VISIBLE
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
            textViewRadarDescription.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_light),
                Typeface.NORMAL
            )
        }

    }

    override fun areBatteryOptimizationsIgnored(): Boolean {
        (context!!.getSystemService(POWER_SERVICE) as PowerManager).let { powerManager ->
            return powerManager.isIgnoringBatteryOptimizations(context!!.packageName)
        }
    }

    @SuppressLint("BatteryLife")
    override fun requestIgnoreBatteryOptimizations() {
        (context!!.getSystemService(POWER_SERVICE) as PowerManager?)?.let {
            if (!it.isIgnoringBatteryOptimizations(activity!!.packageName)) {
                try {
                    startActivityForResult(Intent().apply {
                        action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                        data = Uri.parse("package:${activity!!.packageName}")
                    }, REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS)
                } catch (e: ActivityNotFoundException) {
                    presenter.onBatteryOptimizationsIgnored()
                }
            }
        }
    }

    override fun showUnableToReportCovidDialog() {
        CMDialog.Builder(context!!)
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
        ExposureHealedDialog(context!!).show()
    }

    private fun showDialogDisableRadarWarning() {
        CMDialog.Builder(context!!)
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
            textViewRadarDescription.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_light),
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
            textViewRadarDescription.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_bold),
                Typeface.NORMAL
            )
        }
    }
}