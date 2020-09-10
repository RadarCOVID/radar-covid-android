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
import es.gob.radarcovid.databinding.FragmentHomeBinding
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeView
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady(arguments?.getBoolean(ARG_ACTIVATE_RADAR) ?: false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        binding.switchRadar.setOnClickListener {
            if (!binding.switchRadar.isChecked) { // The status is already change when onclick is executed
                binding.switchRadar.isChecked = true
                binding.switchRadar.jumpDrawablesToCurrentState()
                showDialogDisableRadarWarning()
            } else {
                binding.switchRadar.isChecked = false
                binding.switchRadar.jumpDrawablesToCurrentState()
                presenter.onSwitchRadarClick(false)
            }
        }
        binding.switchRadar.setOnTouchListener { _, event ->
            event.actionMasked == MotionEvent.ACTION_MOVE
        }

        binding.imageViewLogo.setOnLongClickListener {
            presenter.onBackgroundImageLongClick()
            true
        }
        binding.imageViewInitializationCheck.setOnLongClickListener {
            presenter.onBackgroundImageLongClick()
            true
        }
        binding.wrapperExposure.setOnClickListener { presenter.onExposureBlockClick() }
        binding.textViewMoreInfo.setOnClickListener {
            presenter.onMoreInfoButtonClick(
                labelManager.getText(
                    "HOME_EXPOSITION_POSITIVE_MORE_INFO_URL",
                    R.string.exposition_block_infected_more_info_url
                ).toString()
            )
        }
        binding.buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
    }

    override fun showInitializationCheckAnimation() {
        binding.imageViewLogo.alpha = 0f
        binding.imageViewInitializationCheck.alpha = 1f
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.imageViewLogo, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(binding.imageViewInitializationCheck, View.ALPHA, 1f, 0f)
            )
            duration = 2000 //set duration for animations
            startDelay = 2000
        }.start()
    }

    override fun showExposureBlockLow() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_expure_low)
        binding.textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_LOW", R.string.exposition_block_low_title)
        binding.textViewExpositionDescription.text =
            labelManager.getText(
                "HOME_EXPOSITION_MESSAGE_LOW",
                R.string.exposition_block_low_description
            )
        binding.textViewExpositionTitle.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.green
            )
        )
        binding.textViewMoreInfo.visibility = View.GONE
    }

    override fun showExposureBlockHigh() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        binding.textViewExpositionTitle.text =
            labelManager.getText("HOME_EXPOSITION_TITLE_HIGH", R.string.exposition_block_high_title)
        binding.textViewExpositionDescription.text =
            labelManager.getFormattedText(
                "HOME_EXPOSITION_MESSAGE_HIGH",
                labelManager.getContactPhone()
            ).default(getString(R.string.exposition_block_high_description)).parseHtml()
        binding.textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
        binding.textViewMoreInfo.visibility = View.GONE
    }

    override fun showExposureBlockInfected() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_infected)
        binding.textViewExpositionTitle.text = labelManager.getText(
            "HOME_EXPOSITION_TITLE_POSITIVE",
            R.string.exposition_block_infected_title
        )
        binding.textViewExpositionDescription.text =
            labelManager.getText(
                "HOME_EXPOSITION_MESSAGE_INFECTED",
                R.string.exposition_block_infected_description
            )
        binding.textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
        binding.textViewMoreInfo.visibility = View.VISIBLE
    }

    override fun showBackgroundEnabled(enabled: Boolean) {
        if (enabled) {
            if (binding.imageViewLogo.colorFilter != null) {
                binding.imageViewLogoBackground.clearColorFilter()
                binding.imageViewLogo.clearColorFilter()
            }
        } else {
            ColorMatrixColorFilter(ColorMatrix().apply {
                setSaturation(0f)
            }).let {
                if (binding.imageViewLogo.colorFilter == null) {
                    binding.imageViewLogoBackground.colorFilter = it
                    binding.imageViewLogo.colorFilter = it
                }
            }
        }
    }

    override fun showReportButton() {
        binding.buttonCovidReport.visibility = View.VISIBLE
    }

    override fun hideReportButton() {
        binding.buttonCovidReport.visibility = View.GONE
    }

    override fun setRadarBlockChecked(checked: Boolean) {
        if (binding.switchRadar.isChecked != checked) {
            binding.switchRadar.isChecked = checked
            showRadarBlockChecked(checked)
        }
    }

    override fun setRadarBlockEnabled(enabled: Boolean) {
        if (enabled) {
            binding.switchRadar.visibility = View.VISIBLE
        } else {
            binding.switchRadar.visibility = View.GONE
            binding.textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_INACTIVE",
                R.string.radar_block_not_checked_title
            )
            binding.textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_MESSAGE_DISABLED",
                R.string.radar_block_disabled_description
            )
            binding.textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.black
                )
            )
            binding.textViewRadarDescription.setTypeface(
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
            .setCloseButton { it.dismiss() }
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
            .setCloseButton { it.dismiss() }
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
            binding.textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_ACTIVE",
                R.string.radar_block_checked_title
            )
            binding.textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_CONTENT_ACTIVE",
                R.string.radar_block_checked_description
            )
            binding.textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.black
                )
            )
            binding.textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_light),
                Typeface.NORMAL
            )
        } else {
            binding.textViewRadarTitle.text = labelManager.getText(
                "HOME_RADAR_TITLE_INACTIVE",
                R.string.radar_block_not_checked_title
            )
            binding.textViewRadarDescription.text = labelManager.getText(
                "HOME_RADAR_CONTENT_INACTIVE",
                R.string.radar_block_not_checked_description
            )
            binding.textViewRadarDescription.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.red
                )
            )
            binding.textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_bold),
                Typeface.NORMAL
            )
        }
    }
}