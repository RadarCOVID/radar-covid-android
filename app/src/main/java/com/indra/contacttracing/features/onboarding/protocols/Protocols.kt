package com.indra.contacttracing.features.onboarding.protocols

interface OnboardingView {

}

interface OnboardingPresenter {

    fun viewReady()

    fun onNavigateToMainButtonClick()

}

interface OnboardingRouter {

    fun navigateToMain()

}