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

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver
import es.gob.radarcovid.common.di.component.DaggerApplicationComponent
import es.gob.radarcovid.features.worker.FakeInfectionReportWorker
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import okhttp3.CertificatePinner
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.models.ApplicationInfo
import org.dpppt.android.sdk.util.SignatureUtil
import javax.inject.Inject
import javax.inject.Named


class RadarCovidApplication : DaggerApplication() {

    @Inject
    lateinit var certificatePinner: CertificatePinner

    @Inject
    @Named("userAgent")
    lateinit var userAgent: String

    override fun onCreate() {
        super.onCreate()

        initRxJavaSettings()

        DP3T.init(
            this,
            ApplicationInfo(packageName, BuildConfig.REPORT_URL, BuildConfig.BUCKET_URL),
            SignatureUtil.getPublicKeyFromBase64OrThrow(BuildConfig.PUBLIC_KEY),
            BuildConfig.DEBUG
        )
        DP3T.setCertificatePinner(certificatePinner)
        DP3T.setUserAgent(userAgent)

        FakeInfectionReportWorker.start(this)

        registerReceiver(ExposureStatusChangeBroadcastReceiver(), DP3T.getUpdateIntentFilter())

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

}