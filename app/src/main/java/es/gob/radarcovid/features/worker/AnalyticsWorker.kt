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

import android.content.Context
import androidx.work.*
import dagger.android.HasAndroidInjector
import es.gob.radarcovid.datamanager.usecase.ExposureInfoUseCase
import es.gob.radarcovid.datamanager.usecase.SendAnalyticsUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AnalyticsWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    companion object {

        private const val TAG = "AnalyticsWorker"

        fun start(context: Context, executionDelay: Int) {
            start(context, executionDelay, ExistingWorkPolicy.KEEP)
        }

        fun start(context: Context, executionDelay: Int, existingWorkPolicy: ExistingWorkPolicy) {
            //Execution control - do not execute if value is negative
            if (executionDelay > 0) {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val work =
                    OneTimeWorkRequest
                        .Builder(AnalyticsWorker::class.java)
                        .setInitialDelay(executionDelay.toLong(), TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag(TAG)
                        .build()
                WorkManager.getInstance(context)
                    .enqueueUniqueWork(TAG, existingWorkPolicy, work)
            } else {
                //Clean pending process
                WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
            }
        }
    }

    @Inject
    lateinit var sendAnalyticsUseCase: SendAnalyticsUseCase

    @Inject
    lateinit var exposureInfoUseCase: ExposureInfoUseCase

    override fun doWork(): Result {
        val period = sendAnalyticsUseCase.getAnalyticsPeriod()

        //Execution control - do not re-schedule if value is negative
        if (period > 0) {
            val exposureInfo = exposureInfoUseCase.getExposureInfo()
            sendAnalyticsUseCase.sendAnalyticsData(exposureInfo)
            start(applicationContext, period, ExistingWorkPolicy.REPLACE)
        }

        return Result.success()
    }
}