package com.indra.contacttracing.features.onboarding.protocols

const val ONBOARDING_PAGE_INDEX_LEGAL_INFO = 0
const val ONBOARDING_PAGE_INDEX_STEP_1 = 1
const val ONBOARDING_PAGE_INDEX_STEP_2 = 2
const val ONBOARDING_PAGE_INDEX_STEP_3 = 3
const val ONBOARDING_PAGE_INDEX_STEP_4 = 4

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