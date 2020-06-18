package es.gob.covidradar.features.helpline.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.covidradar.features.helpline.protocols.HelplineRouter
import es.gob.covidradar.features.poll.main.view.PollActivity
import javax.inject.Inject

class HelplineRouterImpl @Inject constructor(private val context: Context) : HelplineRouter {

    override fun navigateToBrowser(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun navigateToPoll() {
        PollActivity.open(context)
    }

}