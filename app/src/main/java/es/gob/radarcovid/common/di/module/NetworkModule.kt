package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.api.ApiInterface
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun providesLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @PerApplication
    fun providesHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
        .build()

    @Provides
    @PerApplication
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @PerApplication
    fun providesApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

}