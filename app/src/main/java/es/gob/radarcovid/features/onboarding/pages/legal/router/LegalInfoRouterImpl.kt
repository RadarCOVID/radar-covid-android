package es.gob.radarcovid.features.onboarding.pages.legal.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import javax.inject.Inject


class LegalInfoRouterImpl @Inject constructor(private val fragment: Fragment) : LegalInfoRouter {

    override fun navigateToConditions() {
        fragment.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(Constants.URL_USAGE_CONDITIONS)
            )
        )
    }

    override fun navigateToPrivacyPolicy() {
        fragment.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(Constants.URL_PRIVACY_POLICY)
            )
        )
    }

}