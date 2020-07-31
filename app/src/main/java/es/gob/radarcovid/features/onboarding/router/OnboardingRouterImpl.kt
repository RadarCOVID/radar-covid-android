package es.gob.radarcovid.features.onboarding.router

import android.content.Context
import android.content.Intent
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.protocols.OnboardingRouter
import es.gob.radarcovid.features.splash.view.SplashActivity
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val context: Context) : OnboardingRouter {

    override fun navigateToMain(activateRadar: Boolean) {
        MainActivity.open(context, activateRadar)
    }

    override fun navigateToSplash() {
        context.startActivity(Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

}