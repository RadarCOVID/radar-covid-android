package com.indra.coronaradar.features.onboarding.router

import android.content.Context
import android.content.Intent
import com.indra.coronaradar.features.main.view.MainActivity
import com.indra.coronaradar.features.onboarding.protocols.OnboardingRouter
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val context: Context) : OnboardingRouter {

    override fun navigateToMain() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

}