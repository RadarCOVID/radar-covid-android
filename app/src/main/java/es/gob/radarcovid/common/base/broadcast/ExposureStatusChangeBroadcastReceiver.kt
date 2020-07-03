package es.gob.radarcovid.common.base.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.nearby.exposurenotification.ExposureNotificationClient
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.events.BUS
import es.gob.radarcovid.common.base.events.EventExposureStatusChange
import es.gob.radarcovid.features.splash.view.SplashActivity
import org.dpppt.android.sdk.DP3T

class ExposureStatusChangeBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ExposureNotificationClient.ACTION_EXPOSURE_STATE_UPDATED -> context?.let {
                showExposureLevelChangeNotification(it)
            }
            DP3T.ACTION_UPDATE -> BUS.post(EventExposureStatusChange())
        }
    }

    private fun showExposureLevelChangeNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(channel)
        }

        val resultIntent = Intent(context, SplashActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent =
            PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification =
            NotificationCompat.Builder(context, context.packageName)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.exposure_change_notification))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()


        notificationManager.notify(NOTIFICATION_ID, notification)

    }

}