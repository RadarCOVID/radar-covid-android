package es.gob.covidradar.features.onboarding.router

import android.content.Context
import es.gob.covidradar.features.main.view.MainActivity
import es.gob.covidradar.features.onboarding.protocols.OnboardingRouter
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val context: Context) : OnboardingRouter {

    override fun navigateToMain(activateRadar: Boolean) {
        MainActivity.open(context, activateRadar)
    }

}