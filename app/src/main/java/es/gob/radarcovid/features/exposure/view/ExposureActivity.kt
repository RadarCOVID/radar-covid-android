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
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.common.extensions.parseHtml
import es.gob.radarcovid.features.exposure.protocols.ExposurePresenter
import es.gob.radarcovid.features.exposure.protocols.ExposureView
import es.gob.radarcovid.features.main.view.ExposureHealedDialog
import kotlinx.android.synthetic.main.activity_exposure.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
import kotlinx.android.synthetic.main.layout_exposure_detail_high.*
import javax.inject.Inject

class ExposureActivity : BaseBackNavigationActivity(), ExposureView {

    companion object {

        private const val EXTRA_VENUE_EXPOSURE = "extra_venue_exposure"

        fun open(context: Context, venueExposure: Boolean) {
            context.startActivity(Intent(context, ExposureActivity::class.java).apply {
                putExtra(EXTRA_VENUE_EXPOSURE, venueExposure)
            })
        }
    }

    @Inject
    lateinit var presenter: ExposurePresenter

    private var _isVenueExposure: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exposure)

        _isVenueExposure = intent.getBooleanExtra(EXTRA_VENUE_EXPOSURE, false)

        initViews()
        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(_isVenueExposure)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun initViews() {
        imageButtonBack.contentDescription =
            "${labelManager.getText("ACC_HOME_TITLE", R.string.title_home)} ${
                labelManager.getText(
                    "ACC_BUTTON_BACK_TO",
                    R.string.navigation_back_to
                )
            }"

        wrapperContactButton.setOnClickListener { presenter.onContactButtonClick() }

        textViewMoreInfo.text = String.format("%s >", textViewMoreInfo.text)
    }

    override fun showExposureLevelLow() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_expure_low)
        textViewExpositionDetailTitleSmall.text = labelManager.getText(
            "EXPOSITION_LOW_TITLE_1",
            R.string.exposure_detail_low_title_small
        )
        textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_LOW_TITLE_2",
            R.string.exposure_detail_low_title
        )
        textViewMoreInfo.visibility = View.GONE

        wrapperExposureLow.visibility = View.VISIBLE
        wrapperExposureHigh.visibility = View.GONE
        wrapperExposureInfected.visibility = View.GONE
    }

    override fun showExposureLevelHigh() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        textViewExpositionDetailTitleSmall.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_1",
            R.string.exposure_detail_high_title_small
        )
        textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_2", R.string.exposure_detail_high_title
        )
        textViewMoreInfo.visibility = View.GONE

        imageViewRisk.visibility = View.VISIBLE
        wrapperExposureHigh.visibility = View.VISIBLE
        wrapperExposureLow.visibility = View.GONE
        wrapperExposureInfected.visibility = View.GONE
    }

    override fun showExposureLevelInfected() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_infected)
        textViewExpositionDetailTitleSmall.text =
            labelManager.getText(
                "EXPOSITION_EXPOSED_TITLE_1",
                R.string.exposure_detail_infected_title_small
            )
        textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_EXPOSED_TITLE_2",
            R.string.exposure_detail_infected_title
        )
        textViewMoreInfo.visibility = View.VISIBLE

        wrapperExposureInfected.visibility = View.VISIBLE
        wrapperExposureLow.visibility = View.GONE
        wrapperExposureHigh.visibility = View.GONE
    }

    override fun showVenueExposureLevelHigh() {
        wrapperExposure.setBackgroundResource(R.drawable.background_shape_exposure_high)
        textViewExpositionDetailTitleSmall.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_1",
            R.string.exposure_detail_high_title_small
        )
        textViewExpositionDetailTitle.text = labelManager.getText(
            "EXPOSITION_HIGH_TITLE_2", R.string.exposure_detail_high_title
        )
        textViewMoreInfo.visibility = View.GONE

        imageViewRisk.setImageResource(R.drawable.ic_risk_place)
        imageViewRisk.visibility = View.VISIBLE
        wrapperExposureHigh.visibility = View.VISIBLE
        wrapperExposureLow.visibility = View.GONE
        wrapperExposureInfected.visibility = View.GONE
    }

    override fun setExposureInfo(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    ) {
        textViewExposureDescription.visibility = View.VISIBLE
        textViewExposureDescription.text =
            labelManager.getExposureHighDatesText(date, daysElapsed, hoursElapsed, minutesElapsed)
    }

    override fun setVenueExposureInfo(date: String, daysElapsed: Int?) {
        textViewExposureDescription.visibility = View.VISIBLE
        textViewExposureDescription.text =
            labelManager.getVenueExposureHighDatesText(date, daysElapsed)
    }

    override fun setDaysToHeal(daysToHeal: Int) {
        if (daysToHeal >= 0) {
            textViewExpositionCount.visibility = View.VISIBLE
            val labelId =
                if (daysToHeal == 1) "EXPOSED_EXPOSITION_COUNT_ONE_DAY" else "EXPOSED_EXPOSITION_COUNT_ANYMORE"
            textViewExpositionCount.text =
                labelManager.getFormattedText(labelId, daysToHeal.toString())
                    .default(getString(R.string.exposition_block_exposure_high_count)).parseHtml()
        } else {
            textViewExpositionCount.visibility = View.INVISIBLE
        }

    }

    override fun showExposureDates(exposureDates: String) {
        wrapperExposureDates.visibility = View.VISIBLE
        textViewExposureDates.text = exposureDates
    }

    override fun hideExposureDates() {
        wrapperExposureDates.visibility = View.GONE
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
        textViewExposureDescription.visibility = View.VISIBLE
        textViewExposureDescription.text =
            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun setLastUpdateNoData() {
        textViewExposureDescription.visibility = View.GONE
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
