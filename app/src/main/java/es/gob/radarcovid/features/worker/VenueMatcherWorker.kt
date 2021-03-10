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
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.usecase.VenueMatcherUseCase
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class VenueMatcherWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    companion object {

        const val ACTION_NEW_VENUE_EXPOSURE_NOTIFICATION =
            "${BuildConfig.APPLICATION_ID}.ACTION_NEW_VENUE_EXPOSURE_NOTIFICATION"
        const val NOTIFICATION_VENUE_EXPOSURE_ID = 4
        private const val TAG = "VenueMatcherWorker"
        private const val KEY_DELAY = "KEY_DELAY"

        fun set(context: Context, minutesToNotify: Int) {
            set(context, minutesToNotify, ExistingWorkPolicy.KEEP)
        }

        fun set(context: Context, minutesToNotify: Int, existingWorkPolicy: ExistingWorkPolicy) {
            val work = OneTimeWorkRequest
                .Builder(VenueMatcherWorker::class.java)
                .setInitialDelay(minutesToNotify.toLong(), TimeUnit.MINUTES)
                .setInputData(
                    Data.Builder().putInt(KEY_DELAY, minutesToNotify).build()
                )
                .addTag(TAG)
                .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(TAG, existingWorkPolicy, work)
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
        }
    }

    @Inject
    lateinit var venueMatcherUseCase: VenueMatcherUseCase

    @Inject
    lateinit var labelManager: LabelManager

    override fun doWork(): Result {

        val delayMinutes = inputData.getInt(
            KEY_DELAY,
            Constants.NOTIFICATION_REMINDER_DEFAULT
        )

        val exposureVenues = venueMatcherUseCase.checkForMatches()
        if (!exposureVenues.isNullOrEmpty()) {
            exposureVenues.firstOrNull { !it.isNotified }?.let {
                showVenueExposureNotification(applicationContext)
                venueMatcherUseCase.setVenueNotified()
            }
            //get last exposed venue
            venueMatcherUseCase.setVenueExposureInfo(exposureVenues.last())
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
                Intent(
                    ACTION_NEW_VENUE_EXPOSURE_NOTIFICATION
                )
            )
        } else {
            venueMatcherUseCase.setVenueExposureInfo(null)
        }

        set(applicationContext, delayMinutes, ExistingWorkPolicy.REPLACE)

        return Result.success()
    }

    private fun showVenueExposureNotification(context: Context) {
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
            "VENUE_EXPOSURE_NOTIFICATION_BODY",
            labelManager.getContactPhone()
        ).default(context.getString(R.string.venue_exposure_notification_body))

        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(
                    labelManager.getFormattedText(
                        "NOTIFICATION_TITLE_EXPOSURE_HIGH",
                        labelManager.getContactPhone()
                    ).default(context.getString(R.string.venue_record_notification_reminder_title))
                )
                .setContentText(
                    message
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_handshakes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .build()

        notificationManager.notify(
            NOTIFICATION_VENUE_EXPOSURE_ID,
            notification
        )
    }
}