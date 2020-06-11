package com.indra.coronaradar.features.main.router

import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.R
import com.indra.coronaradar.features.health.view.HealthFragment
import com.indra.coronaradar.features.helpline.view.HelplineFragment
import com.indra.coronaradar.features.home.view.HomeFragment
import com.indra.coronaradar.features.main.protocols.MainRouter
import com.indra.coronaradar.features.mydata.view.MyDataFragment
import javax.inject.Inject

class MainRouterImpl @Inject constructor(private val activity: AppCompatActivity) : MainRouter {

    override fun navigateToHome() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.wrapperContent, HomeFragment.newInstance())
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