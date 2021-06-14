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

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.datamanager.utils.LabelManager
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_information.*
import javax.inject.Inject

class InformationDialog(context: Context) : LayoutContainer {

    private val dialog: Dialog

    override val containerView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_information, null)

    @Inject
    lateinit var labelManager: LabelManager

    init {
        initializeInjector(context)

        dialog = AlertDialog.Builder(context)
            .setView(containerView)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        buttonClose.setOnClickListener { dialog.dismiss() }
        buttonCancel.setOnClickListener { dialog.dismiss() }

        setImageContentDescription()
    }

    fun show() {
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun initializeInjector(context: Context) {
        (context.applicationContext as? HasAndroidInjector)?.androidInjector()?.inject(this)
    }

    private fun setImageContentDescription() {
        val imageDescription = labelManager.getText(
            "ACC_IMAGE_DESCRIPTION",
            R.string.image_desc
        ).toString()

        val imageBluetooth = labelManager.getText(
            "INFO_HELP_BLUETOOTH_IMAGE",
            R.string.information_popup_bluetooth_image
        ).toString()
        imageViewBluetooth.contentDescription = "$imageBluetooth $imageDescription"

        val imageRadar = labelManager.getText(
            "INFO_HELP_RADAR_TITLE",
            R.string.information_popup_radar
        ).toString()
        imageViewRadar.contentDescription = "$imageRadar $imageDescription"

        val imgGoogle = labelManager.getText(
            "INFO_HELP_NOTIFICATIONS_STEP_2",
            R.string.information_popup_notifications_step_2
        ).toString().substring(2)
        imageViewGooogle.contentDescription = "$imgGoogle $imageDescription"

        val imgExposure = labelManager.getText(
            "INFO_HELP_NOTIFICATIONS_TITLE",
            R.string.information_popup_notifications_title
        ).toString()
        imageViewNotification.contentDescription = "$imgExposure $imageDescription"

        val imgExposureNootification = labelManager.getText(
            "INFO_HELP_NOTIFICATIONS_IMAGE",
            R.string.information_popup_notifications_image
        ).toString()
        imageViewNotificationActive.contentDescription =
            "$imgExposureNootification $imageDescription"
    }
}