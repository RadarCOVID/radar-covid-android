package com.indra.contacttracing.features.helpline.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.indra.contacttracing.features.helpline.protocols.HelplineRouter
import javax.inject.Inject

class HelplineRouterImpl @Inject constructor(private val context: Context) : HelplineRouter {

    override fun navigateToBrowser(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

}