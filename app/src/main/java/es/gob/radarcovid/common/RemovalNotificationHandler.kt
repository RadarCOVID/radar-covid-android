/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class RemovalNotificationHandler @Inject constructor(private val labelManager: LabelManager){


    fun scheduleNotification(context: Context) {
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
                        "NOTIFICATION_TITLE_REMOVAL",
                        R.string.notification_title_removal
                    )
                )
                .setContentText(
                    labelManager.getFormattedText(
                        "NOTIFICATION_MESSAGE_REMOVAL",
                        labelManager.getContactPhone()
                    ).default(context.getString(R.string.notification_message_removal))
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_handshakes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()


        notificationManager.notify(ExposureStatusChangeBroadcastReceiver.NOTIFICATION_ID, notification)
    }

}