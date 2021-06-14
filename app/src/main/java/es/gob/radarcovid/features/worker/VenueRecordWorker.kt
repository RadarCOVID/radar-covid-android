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
import androidx.work.*
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.common.extensions.default
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.datamanager.usecase.VenueRecordUseCase
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.splash.view.SplashActivity
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

enum class VenueRecordNotificationType {
    REMINDER, AUTO_CHECKOUT
}

class VenueRecordWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    @Inject
    lateinit var labelManager: LabelManager

    @Inject
    lateinit var venueRecordUseCase: VenueRecordUseCase

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    companion object {

        const val NOTIFICATION_REMINDER_ID = 3
        private const val TAG = "VenueRecordWorker"
        private const val KEY_DELAY = "KEY_DELAY"

        fun set(context: Context, minutesToNotify: Int) {
            val work = OneTimeWorkRequest
                .Builder(VenueRecordWorker::class.java)
                .setInitialDelay(minutesToNotify.toLong(), TimeUnit.MINUTES)
                .setInputData(
                    Data.Builder().putInt(KEY_DELAY, minutesToNotify).build()
                )
                .addTag(TAG)
                .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, work)
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
        }

    }

    override fun doWork(): Result {
        val delayMinutes = inputData.getInt(
            KEY_DELAY,
            Constants.NOTIFICATION_REMINDER_DEFAULT
        )

        val currentVenue = venueRecordUseCase.getCurrentVenue()
        val millisElapsed = System.currentTimeMillis() - currentVenue?.dateIn?.time!!
        val daysElapsed = TimeUnit.MILLISECONDS.toDays(millisElapsed)
        val hoursElapsed = TimeUnit.MILLISECONDS.toHours(millisElapsed) - (daysElapsed * 24)
        val minutesElapsed =
            TimeUnit.MILLISECONDS.toMinutes(millisElapsed) - (hoursElapsed * 60)

        if (minutesElapsed > preferencesRepository.getAutoCheckoutTime() && !preferencesRepository.isApplicationActive()) {
            //Auto checkout only if app is in background
            venueRecordUseCase.checkOut(Date(), moreFiveHours = false).blockingAwait()
            showVenueRecordNotification(
                applicationContext,
                VenueRecordNotificationType.AUTO_CHECKOUT
            )
        } else {
            showVenueRecordNotification(applicationContext, VenueRecordNotificationType.REMINDER)
            set(applicationContext, delayMinutes)
        }
        return Result.success()
    }

    private fun showVenueRecordNotification(context: Context, type: VenueRecordNotificationType) {
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

        val splashIntent = SplashActivity.getIntent(context, false).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                splashIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val message = if (type == VenueRecordNotificationType.REMINDER)
            labelManager.getFormattedText(
                "VENUE_RECORD_NOTIFICATION_REMINDER_BODY"
            ).default(context.getString(R.string.venue_record_notification_reminder_body))
        else
            labelManager.getFormattedText(
                "NOTIFICATION_AUTO_CHECKOUT_MESSAGE",
                (preferencesRepository.getAutoCheckoutTime() / 60).toString()
            ).default(context.getString(R.string.venue_record_notification_autocheckout_message))

        val title = if (type == VenueRecordNotificationType.REMINDER)
            labelManager.getFormattedText(
                "VENUE_RECORD_NOTIFICATION_REMINDER_TITLE"
            ).default(context.getString(R.string.venue_record_notification_reminder_title))
        else
            labelManager.getFormattedText(
                "NOTIFICATION_AUTO_CHECKOUT_TITLE"
            ).default(context.getString(R.string.venue_record_notification_autocheckout_title))

        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
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