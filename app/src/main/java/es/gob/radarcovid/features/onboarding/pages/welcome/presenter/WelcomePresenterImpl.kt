package es.gob.radarcovid.features.onboarding.pages.welcome.presenter

import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomePresenter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeRouter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeView
import javax.inject.Inject

class WelcomePresenterImpl @Inject constructor(
    private val view: WelcomeView,
    private val router: WelcomeRouter
) : WelcomePresenter {

    override fun viewReady() {

    }

    override fun onContinueButtonClick() {
        if (view.isLocaleChanged())
            view.showLanguageChangeDialog()
        else
            view.performContinueButtonClick()
    }

    override fun onLocaleChangeConfirm() {
        view.applyLocaleSettings()
    }

    override fun onLocaleChangeCancel() {
        view.restorePreviousLanguage()
    }

}