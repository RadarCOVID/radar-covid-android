package es.gob.radarcovid.features.locale.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionRouter
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class LocaleSelectionRouterImpl @Inject constructor(
    private val context: Context
) : LocaleSelectionRouter {

    override fun restartApplication() {
        (context as? Activity)?.finish()
        context.startActivity(Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

}