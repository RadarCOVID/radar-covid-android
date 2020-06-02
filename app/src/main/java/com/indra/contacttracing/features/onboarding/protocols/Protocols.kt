package com.indra.contacttracing.features.onboarding.protocols

interface OnboardingView {

    fun showPreviousPage()

    fun showNextPage()

    fun finish()

}

interface OnboardingPresenter {

    fun viewReady()

    fun onBackButtonPressed()

    fun onContinueButtonClick(page: Int, totalPages: Int)

}

interface OnboardingRouter {

    fun navigateToMain()

}