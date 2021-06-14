/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid

import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver
import es.gob.radarcovid.common.di.component.DaggerApplicationComponent
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import es.gob.radarcovid.features.worker.FakeInfectionReportWorker
import es.gob.radarcovid.features.worker.VenueMatcherWorker
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import okhttp3.CertificatePinner
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.models.ApplicationInfo
import org.dpppt.android.sdk.util.SignatureUtil
import javax.inject.Inject
import javax.inject.Named


class RadarCovidApplication : DaggerApplication(), LifecycleObserver {

    @Inject
    lateinit var certificatePinner: CertificatePinner

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    @Named("userAgent")
    lateinit var userAgent: String

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        initRxJavaSettings()

        DP3T.init(
            this,
            ApplicationInfo(BuildConfig.REPORT_URL, BuildConfig.BUCKET_URL),
            SignatureUtil.getPublicKeyFromBase64OrThrow(BuildConfig.PUBLIC_KEY),
            BuildConfig.DEBUG
        )
        DP3T.setCertificatePinner(certificatePinner)
        DP3T.setUserAgent { userAgent }
        DP3T.setErrorNotificationGracePeriod(0)

        FakeInfectionReportWorker.start(this, preferencesRepository)

        registerReceiver(ExposureStatusChangeBroadcastReceiver(), DP3T.getUpdateIntentFilter())
        LocalBroadcastManager.getInstance(this).registerReceiver(
            ExposureStatusChangeBroadcastReceiver(),
            IntentFilter(VenueMatcherWorker.ACTION_NEW_VENUE_EXPOSURE_NOTIFICATION)
        )

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .build()

    private fun initRxJavaSettings() {
        RxJavaPlugins.setErrorHandler {
            if (BuildConfig.DEBUG)
                it.printStackTrace()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        preferencesRepository.setApplicationActive(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        preferencesRepository.setApplicationActive(true)
    }

}