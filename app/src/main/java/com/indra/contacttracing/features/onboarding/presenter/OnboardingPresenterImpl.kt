package com.indra.contacttracing.features.onboarding.presenter

import com.indra.contacttracing.features.onboarding.protocols.OnboardingPresenter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingRouter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingView
import javax.inject.Inject

class OnboardingPresenterImpl @Inject constructor(
    private val view: OnboardingView,
    private val router: OnboardingRouter
) : OnboardingPresenter {

    override fun viewReady() {

    }

    override fun onBackButtonPressed() {
        view.showPreviousPage()
    }

    override fun onContinueButtonClick(page: Int, totalPages: Int) {
        if (page == totalPages - 1) {
            router.navigateToMain()
            view.finish()
        } else {
            view.showNextPage()
        }
    }

}