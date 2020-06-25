package es.gob.radarcovid.features.helpline.router

import android.app.Activity
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import es.gob.radarcovid.features.poll.main.view.PollActivity
import javax.inject.Inject

class HelplineRouterImpl @Inject constructor(private val activity: Activity) : HelplineRouter {

    override fun navigateToBrowser(url: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun navigateToPoll() {
        PollActivity.openForResult(activity, PollActivity.REQUEST_CODE_POLL_COMPLETED)
    }

}