package es.gob.covidradar.features.onboarding.pages.legal.presenter

import es.gob.covidradar.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import es.gob.covidradar.features.onboarding.pages.legal.protocols.LegalInfoRouter
import es.gob.covidradar.features.onboarding.pages.legal.protocols.LegalInfoView
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

    override fun onLegalTermsButtonClick() {
        router.navigateToLegalInfoDetail()
    }

    override fun onPrivacyPolicyButtonClick() {
        router.navigateToPrivacyPolicy()
    }

    override fun onLegalTermsCheckedChange(checked: Boolean) {
        if (checked)
            view.hideCheckWarning()
        else
            view.showCheckWarning()

        view.setContinueButtonEnabled(checked)

    }

}