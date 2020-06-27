package es.gob.radarcovid.common.base.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange

class ExposureStatusChangeBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        BUS.post(EventExposureStatusChange())
    }

}