package es.gob.radarcovid.features.onboarding.pages.legal.presenter

import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoView
import javax.inject.Inject

class LegalInfoPresenterImpl @Inject constructor(
    private val view: LegalInfoView,
    private val router: LegalInfoRouter
) : LegalInfoPresenter {

    override fun viewReady() {

    }

    override fun onLegalTermsAccepted() {
        view.setLegalTermsChecked()
    }

    override fun onConditionsButtonClick() {
        router.navigateToConditions()
    }

    override fun onPrivacyPolicyButtonClick() {
        router.navigateToPrivacyPolicy()
    }

    override fun onLegalTermsCheckedChange(checked: Boolean) {

        view.setContinueButtonEnabled(checked)

    }

}