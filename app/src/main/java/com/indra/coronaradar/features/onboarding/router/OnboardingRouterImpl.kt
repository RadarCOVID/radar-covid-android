package com.indra.coronaradar.features.onboarding.router

import android.content.Context
import com.indra.coronaradar.features.main.view.MainActivity
import com.indra.coronaradar.features.onboarding.protocols.OnboardingRouter
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val context: Context) : OnboardingRouter {

    override fun navigateToMain(activateRadar: Boolean) {
        MainActivity.open(context, activateRadar)
    }

}