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
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import es.gob.radarcovid.models.domain.ExposureInfo
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.internal.AppConfigManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HealerWorker(context: Context, workerParams: WorkerParameters) :
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

        private const val TAG = "HealerWorker"

        fun set(context: Context, minutesToHeal: Long) {
            if (minutesToHeal > 0) {
                val work = OneTimeWorkRequest
                    .Builder(HealerWorker::class.java)
                    .setInitialDelay(minutesToHeal, TimeUnit.MINUTES)
                    .addTag(TAG)
                    .build()
                WorkManager.getInstance(context)
                    .enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, work)
            }
        }

    }


    override fun doWork(): Result {
        when (exposureInfoUseCase.getExposureInfo().level) {
            ExposureInfo.Level.INFECTED -> {
                with(AppConfigManager.getInstance(applicationContext)) {
                    if (!iAmInfectedIsResettable)
                        iAmInfectedIsResettable = true
                }
                exposureInfoUseCase.resetExposureDays()
                DP3T.resetInfectionStatus(applicationContext)
                showLowExposureNotification(applicationContext, true)
            }
            ExposureInfo.Level.HIGH -> {
                exposureInfoUseCase.resetExposureDays()
                showLowExposureNotification(applicationContext, false)
            }
            else -> {
            }
        }
        return Result.success()
    }

    private fun showLowExposureNotification(context: Context, activateRadar: Boolean) {
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

        val splashIntent = SplashActivity.getIntent(context, activateRadar).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, splashIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(
                    labelManager.getText(
                        "NOTIFICATION_TITLE_EXPOSURE_LOW",
                        R.string.exposure_low_notification_title
                    )
                )
                .setContentText(
                    if (activateRadar) {
                        labelManager.getFormattedText(
                            "NOTIFICATION_MESSAGE_EXPOSURE_LOW_ACTIVATE_RADAR",
                            labelManager.getContactPhone()
                        )
                            .default(context.getString(R.string.exposure_low_notification_activate_radar))
                    } else {
                        labelManager.getFormattedText(
                            "NOTIFICATION_MESSAGE_EXPOSURE_LOW",
                            labelManager.getContactPhone()
                        ).default(context.getString(R.string.exposure_low_notification))
                    }
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_handshakes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()


        notificationManager.notify(
            ExposureStatusChangeBroadcastReceiver.NOTIFICATION_ID,
            notification
        )

    }

}