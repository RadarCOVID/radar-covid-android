package es.gob.radarcovid.features.onboarding.pages.welcome.protocols

interface WelcomeView {

    fun isLocaleChanged(): Boolean

    fun applyLocaleSettings()

    fun restorePreviousLanguage()

    fun performContinueButtonClick()

    fun showLanguageChangeDialog()

    fun finish()

}

interface WelcomePresenter {

    fun viewReady()

    fun onContinueButtonClick()

    fun onLocaleChangeConfirm()

    fun onLocaleChangeCancel()

}

interface WelcomeRouter {

    fun navigateToSplash()

}