/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.splash.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.splash.protocols.SplashPresenter
import es.gob.radarcovid.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    companion object {

        private const val EXTRA_ACTIVATE_RADAR = "extra_activate_radar"

        fun open(context: Context, activateRadar: Boolean) =
            context.startActivity(getIntent(context, activateRadar))

        fun getIntent(context: Context, activateRadar: Boolean) =
            Intent(context, SplashActivity::class.java).apply {
                putExtra(EXTRA_ACTIVATE_RADAR, activateRadar)
            }
    }

    @Inject
    lateinit var presenter: SplashPresenter

    private var currentDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.viewReady(intent.getBooleanExtra(EXTRA_ACTIVATE_RADAR, false))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showNoInternetWarning() {
        currentDialog?.dismiss()
        currentDialog = CMDialog.Builder(this)
            .setTitle(
                labelManager.getText(
                    "ALERT_NETWORK_ERROR_TITLE",
                    R.string.warning_connection_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "ALERT_NETWORK_ERROR_MESSAGE",
                    R.string.warning_connection_description
                ).toString()
            )
            .setNegativeButton(
                labelManager.getText(
                    "ACC_BUTTON_CLOSE",
                    R.string.dialog_close_button_description
                ).toString()
            ) {
                it.dismiss()
                presenter.onNetworkDialogCloseButtonClick()
            }
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_RETRY_BUTTON",
                    R.string.warning_connection_button
                ).toString()
            ) {
                it.dismiss()
                presenter.onNetworkRetryButtonClick()
            }
            .build()
            .apply {
                show()
            }
    }

    override fun showPlayServicesRequiredDialog() {
        currentDialog?.dismiss()
        currentDialog = AlertDialog.Builder(this)
            .setTitle(R.string.play_services_dialog_title)
            .setMessage(R.string.play_services_dialog_message)
            .setPositiveButton(
                labelManager.getText("ALERT_ACCEPT_BUTTON", R.string.accept)
            ) { _, _ -> presenter.onInstallPlayServicesButtonClick() }
            .setCancelable(false)
            .create()
        currentDialog?.show()
    }

    override fun showNeedUpdateDialog() {
        currentDialog?.dismiss()
        currentDialog = CMDialog.Builder(this)
            .setTitle(
                labelManager.getText(
                    "ALERT_UPDATE_TEXT_TITLE",
                    R.string.warning_need_update_title
                ).toString()
            )
            .setMessage(
                labelManager.getText(
                    "ALERT_UPDATE_TEXT_CONTENT",
                    R.string.warning_need_update_message
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_UPDATE_BUTTON",
                    R.string.warning_need_update_button
                ).toString()
            ) {
                presenter.onUpdateAppButtonClick()
            }
            .build()
            .apply { show() }
    }

    override fun reloadLabels() {
        labelManager.reload()
    }

}
