package es.gob.radarcovid.features.onboarding.pages.welcome.router

import android.content.Context
import android.content.Intent
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeRouter
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class WelcomeRouterImpl @Inject constructor(private val context: Context) : WelcomeRouter {

    override fun navigateToSplash() {
        context.startActivity(Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }


}