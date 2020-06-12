package com.indra.coronaradar.features.onboarding.protocols

const val ONBOARDING_PAGE_INDEX_LEGAL_INFO = 0
const val ONBOARDING_PAGE_INDEX_STEP_1 = 1
const val ONBOARDING_PAGE_INDEX_STEP_2 = 2
const val ONBOARDING_PAGE_INDEX_STEP_3 = 3

interface OnboardingView {

    fun showPreviousPage()

    fun showNextPage()

    fun isBluetoothEnabled(): Boolean

    fun showBluetoothRequest()

    fun showExitConfirmationDialog()

    fun finish()

}

interface OnboardingPresenter {

    fun viewReady()

    fun onExitConfirmed()

    fun onBackButtonPressed(isFirstItem: Boolean)

    fun onContinueButtonClick(page: Int, totalPages: Int)

    fun onBluetoothEnabled()

}

interface OnboardingRouter {

    fun navigateToMain()

}