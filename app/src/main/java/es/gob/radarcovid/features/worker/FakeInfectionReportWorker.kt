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
import es.gob.radarcovid.datamanager.usecase.ReportFakeInfectionUseCase
import org.dpppt.android.sdk.BuildConfig
import org.dpppt.android.sdk.DP3T
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class FakeInfectionReportWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    companion object {

        private const val TAG = "FakeInfectionReportWorker"

        fun start(context: Context) {
            start(context, getRandomDelay(), ExistingWorkPolicy.KEEP)
        }

        private fun start(
            context: Context,
            delayInMinutes: Long,
            existingWorkPolicy: ExistingWorkPolicy
        ) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work =
                OneTimeWorkRequest
                    .Builder(FakeInfectionReportWorker::class.java)
                    .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(TAG, existingWorkPolicy, work)
        }

        private fun getRandomDelay(): Long = Random.nextLong(180, 360)

    }

    @Inject
    lateinit var reportFakeInfectionUseCase: ReportFakeInfectionUseCase

    override fun doWork(): Result {
        if (BuildConfig.DEBUG)
            DP3T.addWorkerStartedToHistory(applicationContext, TAG)
        reportFakeInfectionUseCase.reportFakeInfection().subscribe()
        start(applicationContext, getRandomDelay(), ExistingWorkPolicy.APPEND)
        return Result.success()
    }
    
}