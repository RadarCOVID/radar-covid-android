package es.gob.radarcovid.features.mydata.router

import androidx.fragment.app.Fragment
import es.gob.radarcovid.features.mydata.protocols.MyDataRouter
import es.gob.radarcovid.features.onboarding.pages.legal.view.PrivacyActivity
import es.gob.radarcovid.features.onboarding.pages.legal.view.ConditionsActivity
import javax.inject.Inject

class MyDataRouterImpl @Inject constructor(private val fragment: Fragment) : MyDataRouter {

    override fun navigateToConditions() {
        ConditionsActivity.openForResult(fragment)
    }

    override fun navigateToPrivacyPolicy() {
        PrivacyActivity.open(fragment.context!!)
    }

}