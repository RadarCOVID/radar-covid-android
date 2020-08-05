package es.gob.radarcovid.features.home.router

import android.content.Context
import android.content.Intent
import com.google.android.gms.nearby.exposurenotification.ExposureNotificationClient
import es.gob.radarcovid.features.covidreport.confirmation.ConfirmationActivity
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.view.ExposureActivity
import es.gob.radarcovid.features.home.protocols.HomeRouter
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(private val context: Context) : HomeRouter {

    override fun navigateToExpositionDetail() = ExposureActivity.open(context)

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

    override fun navigateToCovidReportConfirmation() = ConfirmationActivity.open(context)

    override fun navigateToExposureNotificationSettings() {
        context.startActivity(Intent(ExposureNotificationClient.ACTION_EXPOSURE_NOTIFICATION_SETTINGS))
    }

}