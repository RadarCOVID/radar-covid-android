package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.BuildConfig
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.datamanager.api.ApiInterface
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URI

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun providesLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @PerApplication
    fun providesCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add(URI(BuildConfig.API_URL).host, BuildConfig.CERTIFICATE_PIN)
            .build()

    @Provides
    @PerApplication
    fun providesHttpClient(
        interceptor: Interceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .certificatePinner(certificatePinner)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
        .build()

    @Provides
    @PerApplication
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @PerApplication
    fun providesApiInterface(retrofitBuilder: Retrofit.Builder): ApiInterface =
        retrofitBuilder.baseUrl(BuildConfig.API_URL)
            .build()
            .create(ApiInterface::class.java)

}