/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.databinding.ActivityExposureBinding
import es.gob.radarcovid.features.exposure.protocols.ExposurePresenter
import es.gob.radarcovid.features.exposure.protocols.ExposureView
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
import javax.inject.Inject

class ExposureActivity : BaseBackNavigationActivity(), ExposureView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ExposureActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: ExposurePresenter

    private lateinit var binding: ActivityExposureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExposureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        presenter.viewReady()
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
        binding.wrapperExposureHigh.textViewOtherSymptomsHigh.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSITION_HIGH_OTHER_SYMPTOMS_URL",
                    R.string.exposure_detail_high_other_symptoms_url
                ).toString()
            )
        }
        binding.wrapperExposureHigh.textViewSymptomsHighWhatToDo.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSITION_HIGH_SYMPTOMS_WHAT_TO_DO_URL",
                    R.string.exposure_detail_high_symptoms_what_to_do_url
                ).toString()
            )
        }
        binding.wrapperExposureLow.textViewSymptomsLowWhatToDo.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSITION_LOW_SYMPTOMS_WHAT_TO_DO_URL",
                    R.string.exposure_detail_low_symptoms_what_to_do_url
                ).toString()
            )
        }
        binding.textViewMoreInfo.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSITION_HIGH_MORE_INFO_URL",
                    R.string.exposure_detail_infected_more_info_url
                ).toString()
            )
        }
        binding.wrapperExposureHigh.wrapperContactButton.root.setOnClickListener { presenter.onContactButtonClick() }
        binding.wrapperExposureLow.buttonMoreInfoLow.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSURE_LOW_INFO_URL",
                    R.string.exposure_detail_info_url
                ).toString()
            )
        }
        binding.wrapperExposureHigh.buttonMoreInfoHigh.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSURE_HIGH_INFO_URL",
                    R.string.exposure_detail_info_url
                ).toString()
            )
        }
        binding.wrapperExposureInfected.buttonMoreInfoInfected.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "EXPOSURE_INFECTED_INFO_URL",
                    R.string.exposure_detail_info_url
                ).toString()
            )
        }
    }

    override fun showExposureLevelLow() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_expure_low)
        binding.textViewExpositionDetailTitleSmall.text = labelManager.getText(
            "EXPOSITION_LOW_TITLE_1",
            R.string.exposure_detail_low_title_small
        )
        binding.textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_LOW_TITLE_2",
            R.string.exposure_detail_low_title
        )
        binding.textViewMoreInfo.visibility = View.GONE

        binding.wrapperExposureLow.root.visibility = View.VISIBLE
        binding.wrapperExposureHigh.root.visibility = View.GONE
        binding.wrapperExposureInfected.root.visibility = View.GONE
    }

    override fun showExposureLevelHigh() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        binding.textViewExpositionDetailTitleSmall.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_1",
            R.string.exposure_detail_high_title_small
        )
        binding.textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_2", R.string.exposure_detail_high_title
        )
        binding.textViewMoreInfo.visibility = View.GONE

        binding.wrapperExposureHigh.root.visibility = View.VISIBLE
        binding.wrapperExposureLow.root.visibility = View.GONE
        binding.wrapperExposureInfected.root.visibility = View.GONE
    }

    override fun showExposureLevelInfected() {
        binding.wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_infected)
        binding.textViewExpositionDetailTitleSmall.text =
            labelManager.getText(
                "EXPOSITION_EXPOSED_TITLE_1",
                R.string.exposure_detail_infected_title_small
            )
        binding.textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_EXPOSED_TITLE_2",
            R.string.exposure_detail_infected_title
        )
        binding.textViewMoreInfo.visibility = View.VISIBLE

        binding.wrapperExposureInfected.root.visibility = View.VISIBLE
        binding.wrapperExposureLow.root.visibility = View.GONE
        binding.wrapperExposureHigh.root.visibility = View.GONE
    }

    override fun setExposureInfo(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    ) {
        binding.textViewExposureDescription.visibility = View.VISIBLE
        binding.textViewExposureDescription.text =
            labelManager.getExposureHighDatesText(date, daysElapsed, hoursElapsed, minutesElapsed)
    }

    override fun showExposureDates(exposureDates: String) {
        binding.wrapperExposureDates.visibility = View.VISIBLE
        binding.textViewExposureDates.text = exposureDates
    }

    override fun hideExposureDates() {
        binding.wrapperExposureDates.visibility = View.GONE
    }

    override fun setInfectionDates(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    ) {
        var text = ""
        if (daysElapsed != null && hoursElapsed != null && minutesElapsed != null) {
            text = when {
                daysElapsed > 0 -> {
                    val daysText =
                        resources.getQuantityString(R.plurals.days, daysElapsed, daysElapsed)
                    getString(R.string.exposure_detail_infected_last_update, daysText, date)
                }
                hoursElapsed > 0 -> {
                    val hoursText =
                        resources.getQuantityString(R.plurals.hours, hoursElapsed, hoursElapsed)
                    getString(R.string.exposure_detail_infected_last_update, hoursText, date)
                }
                else -> {
                    val minutesText =
                        resources.getQuantityString(
                            R.plurals.minutes,
                            minutesElapsed,
                            minutesElapsed
                        )
                    getString(R.string.exposure_detail_infected_last_update, minutesText, date)
                }
            }
            labelManager.getFormattedText(
                "EXPOSITION_EXPOSED_DESCRIPTION",
                daysElapsed.toString(),
                date
            ).let {
                if (it.isNotEmpty())
                    text = it
            }

        }
        binding.textViewExposureDescription.visibility = View.VISIBLE
        binding.textViewExposureDescription.text =
            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun setLastUpdateNoData() {
        binding.textViewExposureDescription.visibility = View.GONE
    }

    override fun showDialerForSupport() {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${labelManager.getContactPhone()}")
        })
    }

    override fun showExposureHealedDialog() {
        ExposureHealedDialog(this).show()
    }

}
