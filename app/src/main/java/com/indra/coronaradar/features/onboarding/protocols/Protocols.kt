package com.indra.coronaradar.features.onboarding.protocols

const val ONBOARDING_PAGE_INDEX_STEP_1 = 0
const val ONBOARDING_PAGE_INDEX_STEP_2 = 1
const val ONBOARDING_PAGE_INDEX_STEP_3 = 2

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

    fun onContinueButtonClick()

    fun onFinishButtonClick(activateRadar: Boolean)

    fun onBluetoothEnabled()

}

interface OnboardingRouter {

    fun navigateToMain(activateRadar: Boolean)

}