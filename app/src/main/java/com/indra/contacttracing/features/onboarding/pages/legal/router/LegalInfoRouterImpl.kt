package com.indra.contacttracing.features.onboarding.pages.legal.router

import android.content.Context
import android.content.Intent
import com.indra.contacttracing.features.onboarding.pages.legal.protocols.LegalInfoRouter
import com.indra.contacttracing.features.onboarding.view.OnboardingActivity
import javax.inject.Inject

class LegalInfoRouterImpl @Inject constructor(private val context: Context) : LegalInfoRouter {

    override fun navigateToOnboardingSteps() {
        context.startActivity(Intent(context, OnboardingActivity::class.java))
    }

}