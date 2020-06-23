package es.gob.radarcovid

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import es.gob.radarcovid.common.di.component.DaggerApplicationComponent
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.dpppt.android.sdk.DP3T
import org.dpppt.android.sdk.models.ApplicationInfo
import org.dpppt.android.sdk.util.SignatureUtil


class CovidRadarApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        initRxJavaSettings()

        val signaturePublicKey =
            SignatureUtil.getPublicKeyFromBase64OrThrow("LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUZrd0V3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFc0ZjRW5PUFk0QU9BS2twdjlIU2RXMkJyaFVDVwp3TDE1SHBxdTV6SGFXeTFXbm8yS1I4RzZkWUo4UU8wdVp1MU02ajh6Nk5HWEZWWmNwdzd0WWVYQXFRPT0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0t")
        DP3T.init(
            this,
            ApplicationInfo(packageName, BuildConfig.REPORT_URL, BuildConfig.BUCKET_URL),
            signaturePublicKey
        )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .build()

    private fun initRxJavaSettings() {
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
    }

}