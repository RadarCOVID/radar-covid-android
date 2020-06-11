package com.indra.coronaradar.common.di.module

import com.indra.coronaradar.BuildConfig
import com.indra.coronaradar.common.di.scope.PerApplication
import com.indra.coronaradar.datamanager.api.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @PerApplication
    fun providesApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

}