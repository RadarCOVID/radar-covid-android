/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainView
import es.gob.radarcovid.features.worker.AnalyticsWorker
import es.gob.radarcovid.features.worker.ReminderWorker
import kotlinx.android.synthetic.main.activity_main.*
import org.dpppt.android.sdk.DP3T
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    companion object {

        private const val EXTRA_ACTIVATE_RADAR = "extra_activate_radar"

        fun open(context: Context, activateRadar: Boolean) =
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra(EXTRA_ACTIVATE_RADAR, activateRadar)
            })

    }

    @Inject
    lateinit var presenter: MainPresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DP3T.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        presenter.viewReady(intent.getBooleanExtra(EXTRA_ACTIVATE_RADAR, false))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(
            bottomNavigation.selectedItemId == R.id.menuItemVenue,
            bottomNavigation.selectedItemId == R.id.menuItemHome
        )
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.onRestart()
    }

    private fun initViews() {
        bottomNavigation.menu.forEach {
            when (it.itemId) {
                R.id.menuItemHome -> it.title =
                    labelManager.getText("ACC_HOME_TITLE", R.string.title_home)
//                R.id.menuItemProfile -> it.title =
//                    labelManager.getText("ACC_MYDATA_TITLE", R.string.title_mydata)
                R.id.menuItemVenue -> it.title =
                    labelManager.getText("ACC_VENUE_TITLE", R.string.title_qr)
                R.id.menuItemHelpline -> it.title =
                    labelManager.getText("ACC_HELPLINE_TITLE", R.string.title_helpline)
                R.id.menuItemStats -> it.title =
                    labelManager.getText("ACC_STATS_TITLE", R.string.title_stats)
                R.id.menuItemSettings -> it.title =
                    labelManager.getText("ACC_SETTINGS_TITLE", R.string.title_settings)
            }
        }
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemHome -> presenter.onHomeButtonClick()
                //R.id.menuItemProfile -> presenter.onProfileButtonClick()
                R.id.menuItemVenue -> presenter.onVenueButtonClick()
                R.id.menuItemHelpline -> presenter.onHelplineButtonClick()
                R.id.menuItemStats -> presenter.onStatsButtonClick()
                R.id.menuItemSettings -> presenter.onSettingsButtonClick()
            }
            true
        }
    }

    override fun setSettingSelected() {
        bottomNavigation.selectedItemId = R.id.menuItemSettings
    }

    override fun setHomeSelected() {
        bottomNavigation.selectedItemId = R.id.menuItemHome
    }

    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.menuItemHome)
            presenter.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.menuItemHome
    }

    override fun updateVenueIcon(isVenueRecordSelected: Boolean) {
        if (isVenueRecordSelected) {
            bottomNavigation.getOrCreateBadge(R.id.menuItemVenue).apply {
                backgroundColor = ContextCompat.getColor(applicationContext, R.color.red_2300)
                number = 1
                badgeTextColor = ContextCompat.getColor(applicationContext, R.color.red_2300)
            }
        } else {
            bottomNavigation.removeBadge(R.id.menuItemVenue)
        }
    }

    override fun showExitConfirmationDialog() {
        CMDialog.Builder(this)
            .setMessage(
                labelManager.getText(
                    "ALERT_EXIT_MESSAGE",
                    R.string.warning_exit_application_message
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_CONFIRM_BUTTON",
                    R.string.warning_exit_application_button
                ).toString()
            ) {
                it.dismiss()
                presenter.onExitConfirmed()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ACC_BUTTON_CLOSE",
                    R.string.dialog_close_button_description
                ).toString()
            ) {
                it.dismiss()
            }
            .build()
            .show()
    }

    override fun startAnalyticsWorker(time: Int) {
        AnalyticsWorker.start(this, time)
    }

    override fun createNotificationReminder(time: Int) {
        ReminderWorker.set(this, time)
    }

    override fun cancelNotificationReminder() {
        ReminderWorker.cancel(this)
    }
}
