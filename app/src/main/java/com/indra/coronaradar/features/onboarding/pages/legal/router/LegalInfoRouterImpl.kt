package com.indra.coronaradar.features.onboarding.pages.legal.router

import androidx.fragment.app.Fragment
import com.indra.coronaradar.features.onboarding.pages.legal.protocols.LegalInfoRouter
import com.indra.coronaradar.features.onboarding.pages.legal.view.PrivacyActivity
import com.indra.coronaradar.features.onboarding.pages.legal.view.TermsAndConditionsActivity
import javax.inject.Inject

class LegalInfoRouterImpl @Inject constructor(private val fragment: Fragment) : LegalInfoRouter {

    override fun navigateToLegalInfoDetail() {
        TermsAndConditionsActivity.openForResult(fragment)
    }

    override fun navigateToPrivacyPolicy() {
        PrivacyActivity.open(fragment.context!!)
    }

}