package es.gob.radarcovid.features.splash.router

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
import es.gob.radarcovid.features.splash.protocols.SplashRouter
import javax.inject.Inject

class SplashRouterImpl @Inject constructor(private val context: Context) : SplashRouter {
    override fun navigateToPlayServicesPage() {
        val playServicesPackageName = "com.google.android.gms"
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$playServicesPackageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$playServicesPackageName")
                )
            )
        }
    }

    override fun navigateToLegalInfo() {
        context.startActivity(Intent(context, LegalInfoFragment::class.java))
    }

    override fun navigateToOnboarding() {
        context.startActivity(Intent(context, OnboardingActivity::class.java))
    }

    override fun navigateToMain() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

}