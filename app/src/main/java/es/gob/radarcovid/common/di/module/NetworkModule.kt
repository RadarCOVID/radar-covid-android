/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.base.Constants.DATE_FORMAT
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.api.ApiInterface
import es.gob.radarcovid.datamanager.api.UserAgentInterceptor
import es.gob.radarcovid.datamanager.attestation.AttestationClient
import es.gob.radarcovid.datamanager.attestation.SafetyNetAttestationClient
import es.gob.radarcovid.datamanager.repository.BuildInfoRepository
import es.gob.radarcovid.datamanager.repository.SystemInfoRepository
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URI
import javax.inject.Named


@Module
class NetworkModule {


    @Provides
    @Named("userAgent")
    @PerApplication
    fun providesUserAgent(
        buildInfoRepository: BuildInfoRepository,
        systemInfoRepository: SystemInfoRepository
    ): String =
        with(buildInfoRepository) {
            "${getPackageName()};${getVersionName()};${getVersionCode()}" +
                    ";Android;${systemInfoRepository.getAndroidRelease()} (${systemInfoRepository.getAndroidIncremental()})"
        }

    @Provides
    @PerApplication
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @PerApplication
    fun providesCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add(URI(BuildConfig.API_URL).host, BuildConfig.CERTIFICATE_PIN)
            .add(URI(BuildConfig.API_URL).host, BuildConfig.CERTIFICATE_PIN_NEW)
            .build()

    @Provides
    @PerApplication
    fun providesHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        userAgentInterceptor: UserAgentInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(userAgentInterceptor)
        .certificatePinner(certificatePinner)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
        .build()

    @Provides
    @PerApplication
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create()
            )
        )

    @Provides
    @PerApplication
    fun providesApiInterface(retrofitBuilder: Retrofit.Builder): ApiInterface =
        retrofitBuilder.baseUrl(BuildConfig.API_URL)
            .build()
            .create(ApiInterface::class.java)


    @Provides
    @PerApplication
    fun providesAttestationClient(@Named("applicationContext") application: Context): AttestationClient = SafetyNetAttestationClient(
        application,
        SafetyNetAttestationClient.AttestationParameters(
            apiKey = BuildConfig.SAFETY_NET_API_KEY,
            apkPackageName = application.packageName,
            requiresBasicIntegrity = false,
            requiresCtsProfile = false,
            requiresHardwareAttestation = false
        )
    )

}