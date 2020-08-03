package es.gob.radarcovid.features.exposure.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.features.covidreport.form.view.CovidReportActivity
import es.gob.radarcovid.features.exposure.protocols.ExposureRouter
import javax.inject.Inject

class ExposureRouterImpl @Inject constructor(private val context: Context) : ExposureRouter {

    override fun navigateToCovidReport() = CovidReportActivity.open(context)

    override fun navigateToBrowser(url: String) {
        val uri: Uri = if (url.contains("http://") || url.contains("https://"))
            Uri.parse(url)
        else
            Uri.parse("http://$url")
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                uri
            )
        )
    }

}