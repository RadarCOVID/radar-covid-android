package es.gob.radarcovid.features.onboarding.pages.legal.protocols

interface LegalInfoView {

    fun setLegalTermsChecked()

    fun setContinueButtonEnabled(enabled: Boolean)

}

interface LegalInfoPresenter {

    fun viewReady()

    fun onLegalTermsAccepted()

    fun onConditionsButtonClick()

    fun onPrivacyPolicyButtonClick()

    fun onLegalTermsCheckedChange(checked: Boolean)

}

interface LegalInfoRouter {

    fun navigateToConditions()

    fun navigateToPrivacyPolicy()

}