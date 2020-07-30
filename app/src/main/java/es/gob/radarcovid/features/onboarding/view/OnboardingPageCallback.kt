package es.gob.radarcovid.features.onboarding.view

interface OnboardingPageCallback {

    fun onContinueButtonClick()

    fun onFinishButtonClick(activateRadar: Boolean)

}