package es.gob.radarcovid.features.exposure.region.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoRouter
import javax.inject.Inject

class RegionInfoRouterImpl @Inject constructor(private val context: Context) : RegionInfoRouter {

    override fun navigateToDialer(phone: String) {
        context.startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phone}")
        })
    }

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