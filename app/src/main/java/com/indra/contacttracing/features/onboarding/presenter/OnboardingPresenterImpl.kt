package com.indra.contacttracing.features.onboarding.presenter

import com.indra.contacttracing.features.onboarding.protocols.OnboardingPresenter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingView
import javax.inject.Inject

class OnboardingPresenterImpl @Inject constructor(private val view:OnboardingView):OnboardingPresenter {

    override fun viewReady() {
        TODO("Not yet implemented")
    }

}