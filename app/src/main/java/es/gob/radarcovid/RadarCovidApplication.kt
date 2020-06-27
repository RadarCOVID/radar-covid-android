package es.gob.radarcovid

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver
import es.gob.radarcovid.common.di.component.DaggerApplicationComponent
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.models.ApplicationInfo
import org.dpppt.android.sdk.util.SignatureUtil


class RadarCovidApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        initRxJavaSettings()

        DP3T.init(
            this,
            ApplicationInfo(packageName, BuildConfig.REPORT_URL, BuildConfig.BUCKET_URL),
            SignatureUtil.getPublicKeyFromBase64OrThrow(BuildConfig.PUBLIC_KEY),
            true
        )

        registerReceiver(ExposureStatusChangeBroadcastReceiver(), DP3T.getUpdateIntentFilter())

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .build()

    private fun initRxJavaSettings() {
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
    }

}