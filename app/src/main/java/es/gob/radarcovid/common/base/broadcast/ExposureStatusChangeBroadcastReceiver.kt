/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.base.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import com.google.android.gms.nearby.exposurenotification.ExposureNotificationClient
import dagger.android.DaggerBroadcastReceiver
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.GetHealingTimeUseCase
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import es.gob.radarcovid.features.worker.HealerWorker
import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.DP3T
import javax.inject.Inject

class ExposureStatusChangeBroadcastReceiver : DaggerBroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }

    @Inject
    lateinit var getHealingTimeUseCase: GetHealingTimeUseCase

    @Inject
    lateinit var exposureInfoUseCase: ExposureInfoUseCase

    @Inject
    lateinit var labelManager: LabelManager

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        when (intent?.action) {
            ExposureNotificationClient.ACTION_EXPOSURE_STATE_UPDATED -> context?.let {
                Handler().postDelayed({ // DELAY INTRODUCED TO GIVE SOME TIME TO DP3T TO UPDATE THE EXPOSURE STATUS
                    if (isExposureLevelHigh(it)) {
                        exposureInfoUseCase.setExposed(true)
                        HealerWorker.set(
                            it,
                            getHealingTimeUseCase.getHealingTime().exposureHighMinutes
                        )
                        showHighExposureNotification(it)
                    }
                }, 2000)
            }
            DP3T.ACTION_UPDATE -> BUS.post(EventExposureStatusChange())
        }
    }

    private fun showHighExposureNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(channel)
        }

        val resultIntent = Intent(context, SplashActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent =
            PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(
                    labelManager.getText(
                        "NOTIFICATION_TITLE_EXPOSURE_HIGH",
                        R.string.exposure_high_notification_title
                    )
                )
                .setContentText(
                    labelManager.getFormattedText(
                        "NOTIFICATION_MESSAGE_EXPOSURE_HIGH",
                        labelManager.getContactPhone()
                    ).default(context.getString(R.string.exposure_high_notification))
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_handshakes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()


        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    private fun isExposureLevelHigh(context: Context) =
        exposureInfoUseCase.getExposureInfo().level == ExposureInfo.Level.HIGH
}