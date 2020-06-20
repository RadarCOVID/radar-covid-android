package es.gob.covidradar.features.mydata.router

import androidx.fragment.app.Fragment
import es.gob.covidradar.features.mydata.protocols.MyDataRouter
import es.gob.covidradar.features.onboarding.pages.legal.view.PrivacyActivity
import es.gob.covidradar.features.onboarding.pages.legal.view.TermsAndConditionsActivity
import javax.inject.Inject

class MyDataRouterImpl @Inject constructor(private val fragment: Fragment) : MyDataRouter {

    override fun navigateToConditions() {
        TermsAndConditionsActivity.openForResult(fragment)
    }

    override fun navigateToPrivacyPolicy() {
        PrivacyActivity.open(fragment.context!!)
    }

}