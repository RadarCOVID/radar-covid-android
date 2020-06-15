package com.indra.coronaradar.features.helpline.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.indra.coronaradar.features.helpline.protocols.HelplineRouter
import com.indra.coronaradar.features.poll.view.PollActivity
import javax.inject.Inject

class HelplineRouterImpl @Inject constructor(private val context: Context) : HelplineRouter {

    override fun navigateToBrowser(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun navigateToPoll() {
        PollActivity.open(context)
    }

}