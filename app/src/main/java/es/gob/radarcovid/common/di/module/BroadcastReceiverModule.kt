package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.base.broadcast.ExposureStatusChangeBroadcastReceiver

@Module
abstract class BroadcastReceiverModule {

    @ContributesAndroidInjector
    abstract fun bindsExposureStatusChangeBroadcastReceiver(): ExposureStatusChangeBroadcastReceiver

}