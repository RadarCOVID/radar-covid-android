package com.indra.coronaradar.features.onboarding.pages.legal.router

import androidx.fragment.app.Fragment
import com.indra.coronaradar.features.onboarding.pages.legal.protocols.LegalInfoRouter
import com.indra.coronaradar.features.onboarding.pages.legal.view.LegalInfoDetailActivity
import javax.inject.Inject

class LegalInfoRouterImpl @Inject constructor(private val fragment: Fragment) : LegalInfoRouter {

    override fun navigateToLegalInfoDetail() {
        LegalInfoDetailActivity.openForResult(fragment)
    }

}