/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.information.view

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.common.extensions.*
import es.gob.radarcovid.features.information.protocols.InformationPresenter
import es.gob.radarcovid.features.information.protocols.InformationView
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.layout_back_navigation.*
import java.util.*
import javax.inject.Inject


class InformationActivity : BaseBackNavigationActivity(), InformationView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, InformationActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: InformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        initViews()
        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showHelpDialog() {
        InformationDialog(this).show()
    }

    override fun showExposureRecordDialog() {
        ExposureRecordDialog(this).show()
    }

    override fun showRadarEnabled(isRadarEnabled: Boolean) {
        if (isRadarEnabled) {
            val activeText = labelManager.getText(
                "ACTIVE",
                R.string.active
            ).toString()
            textViewRadarStatus.text = activeText
            textViewNotificationStatus.text = activeText
            textViewRadarStatus.setTextColor(ContextCompat.getColor(this, R.color.green_53))
            textViewNotificationStatus.setTextColor(ContextCompat.getColor(this, R.color.green_53))
            imageViewRadarStatus.setImageResource(R.drawable.ic_active)
            imageViewNotificationStatus.setImageResource(R.drawable.ic_active)
        } else {
            val inactiveText = labelManager.getText(
                "INACTIVE",
                R.string.inactive
            ).toString()
            textViewRadarStatus.text = inactiveText
            textViewNotificationStatus.text = inactiveText
            textViewRadarStatus.setTextColor(ContextCompat.getColor(this, R.color.red_00))
            textViewNotificationStatus.setTextColor(ContextCompat.getColor(this, R.color.red_00))
            imageViewRadarStatus.setImageResource(R.drawable.ic_inactive)
            imageViewNotificationStatus.setImageResource(R.drawable.ic_inactive)
        }
    }

    override fun showLastUpdateDate(date: Date, daysElapsed: Int, locale: String) {

        if (date.time != 0L) {
            val dateText = labelManager.getText(
                "INFO_DATE_FORMAT",
                R.string.information_date_format
            ).toString()
                .replaceFirst("\$D", date.getDayString())
                .replaceFirst("\$M", date.geMonthName(locale))
                .replaceFirst("\$Y", date.getYearString())
                .replaceFirst("\$H", date.getHourString())
            textViewTitleSyncDate.text = dateText

            val labelId =
                when (daysElapsed) {
                    0 -> "INFO_LAST_SYNC_UPDATE"
                    1 -> "INFO_LAST_SYNC_ONE_DAY"
                    else -> "INFO_LAST_SYNC_ANYMORE"
                }
            textViewSyncStatus.text = labelManager.getFormattedText(
                labelId,
                daysElapsed.toString()
            ).default(getString(R.string.information_last_sync_one_day))

            if (daysElapsed > 1) {
                textViewSyncStatus.setTextColor(ContextCompat.getColor(this, R.color.red_00))
                imageViewSyncStatus.setImageResource(R.drawable.ic_inactive)
            }

        } else {
            val dateText = labelManager.getText(
                "INFO_LAST_NO_SYNC_UPDATE",
                R.string.information_no_last_update
            ).toString()
            textViewTitleSyncDate.text = "--"
            textViewSyncStatus.text = dateText
            textViewSyncStatus.setTextColor(ContextCompat.getColor(this, R.color.blue_E2))
            imageViewSyncStatus.setImageResource(R.drawable.ic_warning)
        }


    }

    override fun showBluetoothEnabled() {
        val isBluetoothEnabled =
            BluetoothAdapter.getDefaultAdapter()?.isEnabled ?: false
        if (isBluetoothEnabled) {
            textViewBluetoothStatus.text = labelManager.getText(
                "ACTIVE",
                R.string.active
            ).toString()
            textViewBluetoothStatus.setTextColor(ContextCompat.getColor(this, R.color.green_53))
            imageViewBluetoothStatus.setImageResource(R.drawable.ic_active)
        } else {
            textViewBluetoothStatus.text = labelManager.getText(
                "INACTIVE",
                R.string.inactive
            ).toString()
            textViewBluetoothStatus.setTextColor(ContextCompat.getColor(this, R.color.red_00))
            imageViewBluetoothStatus.setImageResource(R.drawable.ic_inactive)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun showTerminalData(locale: String) {
        textViewVersion.text = BuildConfig.VERSION_NAME
        textViewSO.text = "${Constants.SO_NAME} ${Build.VERSION.RELEASE}"
        textViewModel.text = "${Build.MANUFACTURER} ${Build.MODEL}"

        val packageManager: PackageManager = packageManager
        val lastUpadateDate = Date(packageManager.getPackageInfo(packageName, 0).lastUpdateTime)
        val dateText = labelManager.getText(
            "INFO_DATE_FORMAT",
            R.string.information_date_format
        ).toString()
            .replaceFirst("\$D", lastUpadateDate.getDayString())
            .replaceFirst("\$M", lastUpadateDate.geMonthName(locale))
            .replaceFirst("\$Y", lastUpadateDate.getYearString())
            .replaceFirst("\$H", lastUpadateDate.getHourString())
        textViewUpdateDate.text = dateText
    }

    private fun initViews() {
        buttonMaintenance.setSafeOnClickListener {
            presenter.onHelpButtonClick()
        }
        buttonExposureRecord.setSafeOnClickListener {
            presenter.onExposureRecodrClick()
        }
        buttonSupportMail.setSafeOnClickListener {
            val mail = labelManager.getText(
                "INFO_TECHNICAL_HELP_EMAIL",
                getString(R.string.information_email_help)
            ).toString()
            val message = labelManager.getFormattedTextHtml(
                "INFO_SUPPORT_MAIL",
                getString(R.string.information_support_mail),
                textViewRadarStatus.text.toString(),
                textViewNotificationStatus.text.toString(),
                textViewBluetoothStatus.text.toString(),
                textViewTitleSyncDate.text.toString(),
                textViewVersion.text.toString(),
                textViewUpdateDate.text.toString(),
                textViewSO.text.toString(),
                textViewModel.text.toString()
            ).toString()
            presenter.onSupportMailClick(message, "", mail)
        }
        imageButtonBack.contentDescription =
            "${labelManager.getText("SETTINGS_TITLE", R.string.title_home)} ${
            labelManager.getText(
                "ACC_BUTTON_BACK_TO",
                R.string.navigation_back_to
            )
            }"
    }

}