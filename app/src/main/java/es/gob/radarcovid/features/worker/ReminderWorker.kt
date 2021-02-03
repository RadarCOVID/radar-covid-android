/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.Constants.NOTIFICATION_REMINDER_DEFAULT
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    @Inject
    lateinit var labelManager: LabelManager

    @Inject
    lateinit var exposureInfoUseCase: ExposureInfoUseCase

    companion object {

        const val NOTIFICATION_REMINDER_ID = 2
        private const val KEY_DELAY = "KEY_DELAY"
        private const val FACTOR_MIN_MILLIS = 60 * 1000L

        private const val TAG = "ReminderWorker"

        fun set(context: Context, minutesToNotify: Int) {
            if (minutesToNotify > 0) {
                val work = OneTimeWorkRequest
                    .Builder(ReminderWorker::class.java)
                    .setInitialDelay(minutesToNotify.toLong(), TimeUnit.MINUTES)
                    .setInputData(Data.Builder().putInt(KEY_DELAY, minutesToNotify).build())
                    .addTag(TAG)
                    .build()
                WorkManager.getInstance(context)
                    .enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, work)
            }
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
        }

    }

    override fun doWork(): Result {
        val exposureInfo = exposureInfoUseCase.getExposureInfo()
        val delayMinutes = inputData.getInt(KEY_DELAY, NOTIFICATION_REMINDER_DEFAULT)
        val now = System.currentTimeMillis()
        val lastSync = exposureInfo.lastUpdateTime.time
        if ((lastSync + (delayMinutes * FACTOR_MIN_MILLIS)) < now) {
            showReminderNotification(applicationContext)
        } else {
            set(applicationContext, delayMinutes)
        }
        return Result.success()
    }

    private fun showReminderNotification(context: Context) {
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

        val splashIntent = SplashActivity.getIntent(context, true).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                splashIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val message = labelManager.getFormattedText(
            "NOTIFICATION_REMINDER_BODY",
            labelManager.getContactPhone()
        ).default(context.getString(R.string.notification_reminder_body))

        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(
                    labelManager.getFormattedText(
                        "NOTIFICATION_REMINDER_TITLE",
                        labelManager.getContactPhone()
                    ).default(context.getString(R.string.notification_reminder_title))
                )
                .setContentText(
                    message
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_handshakes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .build()

        notificationManager.notify(
            NOTIFICATION_REMINDER_ID,
            notification
        )
    }

}