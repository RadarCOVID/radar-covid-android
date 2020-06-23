package es.gob.radarcovid.features.onboarding.router

import android.content.Context
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.protocols.OnboardingRouter
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val context: Context) : OnboardingRouter {

    override fun navigateToMain(activateRadar: Boolean) {
        MainActivity.open(context, activateRadar)
    }

}