package es.gob.covidradar.features.main.router

import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.R
import es.gob.covidradar.features.health.view.HealthFragment
import es.gob.covidradar.features.helpline.view.HelplineFragment
import es.gob.covidradar.features.home.view.HomeFragment
import es.gob.covidradar.features.main.protocols.MainRouter
import es.gob.covidradar.features.mydata.view.MyDataFragment
import javax.inject.Inject

class MainRouterImpl @Inject constructor(private val activity: AppCompatActivity) : MainRouter {

    override fun navigateToHome(activateRadar: Boolean) {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HomeFragment.newInstance(activateRadar))
            .commit()
    }

    override fun navigateToHealth() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HealthFragment.newInstance())
            .commit()
    }

    override fun navigateToProfile() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, MyDataFragment.newInstance())
            .commit()
    }

    override fun navigateToHelpline() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HelplineFragment.newInstance())
            .commit()
    }

}