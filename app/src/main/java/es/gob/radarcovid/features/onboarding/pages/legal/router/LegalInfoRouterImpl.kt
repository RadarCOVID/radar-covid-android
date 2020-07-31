package es.gob.radarcovid.features.onboarding.pages.legal.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import es.gob.radarcovid.common.base.Constants
import es.gob.radarcovid.datamanager.utils.LabelManager
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoRouter
import javax.inject.Inject


class LegalInfoRouterImpl @Inject constructor(
    private val fragment: Fragment,
    private val labelManager: LabelManager
) : LegalInfoRouter {

    override fun navigateToConditions() {
        fragment.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                getUsageConditionsUri()
            )
        )
    }

    override fun navigateToPrivacyPolicy() {
        fragment.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                getPrivacyPolicyUri()
            )
        )
    }

    private fun getUsageConditionsUri(): Uri {
        val url =
            labelManager.getText("USE_CONDITIONS_URL", Constants.URL_USAGE_CONDITIONS).toString()
        return if (url.contains("http://") || url.contains("https://"))
            Uri.parse(url)
        else
            Uri.parse("http://$url")
    }

    private fun getPrivacyPolicyUri(): Uri {
        val url =
            labelManager.getText("PRIVACY_POLICY_URL", Constants.URL_PRIVACY_POLICY).toString()
        return if (url.contains("http://") || url.contains("https://"))
            Uri.parse(url)
        else
            Uri.parse("http://$url")
    }

}