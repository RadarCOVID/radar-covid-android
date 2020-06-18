package es.gob.covidradar.features.onboarding.pages.legal.router

import androidx.fragment.app.Fragment
import es.gob.covidradar.features.onboarding.pages.legal.protocols.LegalInfoRouter
import es.gob.covidradar.features.onboarding.pages.legal.view.PrivacyActivity
import es.gob.covidradar.features.onboarding.pages.legal.view.TermsAndConditionsActivity
import javax.inject.Inject

class LegalInfoRouterImpl @Inject constructor(private val fragment: Fragment) : LegalInfoRouter {

    override fun navigateToLegalInfoDetail() {
        TermsAndConditionsActivity.openForResult(fragment)
    }

    override fun navigateToPrivacyPolicy() {
        PrivacyActivity.open(fragment.context!!)
    }

}