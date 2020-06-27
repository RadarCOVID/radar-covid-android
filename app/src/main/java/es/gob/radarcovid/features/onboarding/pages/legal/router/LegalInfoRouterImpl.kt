package es.gob.radarcovid.features.onboarding.pages.legal.router

import androidx.fragment.app.Fragment
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import es.gob.radarcovid.features.onboarding.pages.legal.view.PrivacyActivity
import es.gob.radarcovid.features.onboarding.pages.legal.view.ConditionsActivity
import javax.inject.Inject

class LegalInfoRouterImpl @Inject constructor(private val fragment: Fragment) : LegalInfoRouter {

    override fun navigateToConditions() {
        ConditionsActivity.openForResult(fragment)
    }

    override fun navigateToPrivacyPolicy() {
        PrivacyActivity.open(fragment.context!!)
    }

}