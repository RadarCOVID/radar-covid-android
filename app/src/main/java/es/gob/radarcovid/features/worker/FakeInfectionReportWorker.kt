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
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.datamanager.usecase.ReportFakeInfectionUseCase
import org.dpppt.android.sdk.DP3T
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.ln

private const val FACTOR_HOUR_MILLIS = 60 * 60 * 1000L
private const val FACTOR_DAY_MILLIS: Long = 24 * FACTOR_HOUR_MILLIS
private const val MAX_DELAY_HOURS: Long = 48
private val SAMPLING_RATE =
    if (BuildConfig.DEBUG) 1.0f else 0.2f
private const val KEY_T_DUMMY = "KEY_T_DUMMY"

class FakeInfectionReportWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
        (context.applicationContext as? HasAndroidInjector)
            ?.androidInjector()
            ?.inject(this)
    }

    companion object {

        private var clock: Clock = ClockImpl()

        private const val TAG = "FakeInfectionReportWorker"

        fun start(context: Context, preferencesRepository: PreferencesRepository) {
            var tDummy: Long = preferencesRepository.getTDummy()
            if (tDummy == -1L) {
                tDummy = clock.currentTimeMillis() + clock.syncInterval()
                preferencesRepository.setTDummy(tDummy)
            }
            start(context, tDummy, ExistingWorkPolicy.KEEP)
        }

        private fun start(
            context: Context,
            tDummy: Long,
            existingWorkPolicy: ExistingWorkPolicy
        ) {

            val now = clock.currentTimeMillis()
            val executionDelay = 0L.coerceAtLeast(tDummy - now)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work =
                OneTimeWorkRequest
                    .Builder(FakeInfectionReportWorker::class.java)
                    .setInitialDelay(executionDelay, TimeUnit.MILLISECONDS)
                    .setConstraints(constraints)
                    .setInputData(Data.Builder().putLong(KEY_T_DUMMY, tDummy).build())
                    .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(TAG, existingWorkPolicy, work)
        }

    }

    @Inject
    lateinit var reportFakeInfectionUseCase: ReportFakeInfectionUseCase

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun doWork(): Result {
        val now = clock.currentTimeMillis()
        var tDummy = inputData.getLong(KEY_T_DUMMY, now)
        while (tDummy < now) {
            // only do request if it was planned to do in the last 48h
            if (tDummy >= now - FACTOR_HOUR_MILLIS * MAX_DELAY_HOURS) {
                if (BuildConfig.DEBUG)
                    DP3T.addWorkerStartedToHistory(applicationContext, TAG)
                reportFakeInfectionUseCase.reportFakeInfection().subscribe()
            }
            tDummy += clock.syncInterval();
            preferencesRepository.setTDummy(tDummy)
        }

        start(applicationContext, tDummy, ExistingWorkPolicy.REPLACE)
        return Result.success()
    }

    interface Clock {
        fun syncInterval(): Long
        fun currentTimeMillis(): Long
    }

    class ClockImpl : Clock {
        override fun syncInterval(): Long {
            val newDelayDays: Double =
                ExponentialDistribution.sampleFromStandard() / SAMPLING_RATE
            return (newDelayDays * FACTOR_DAY_MILLIS).toLong()
        }

        override fun currentTimeMillis(): Long {
            return System.currentTimeMillis()
        }
    }

    object ExponentialDistribution {
        fun sampleFromStandard(): Double {
            val random = SecureRandom()
            return -ln(1.0 - random.nextDouble())
        }
    }

}