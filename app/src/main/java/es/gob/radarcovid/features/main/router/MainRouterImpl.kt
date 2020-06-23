package es.gob.radarcovid.features.main.router

import androidx.appcompat.app.AppCompatActivity
import es.gob.radarcovid.R
import es.gob.radarcovid.features.health.view.HealthFragment
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import es.gob.radarcovid.features.home.view.HomeFragment
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.mydata.view.MyDataFragment
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