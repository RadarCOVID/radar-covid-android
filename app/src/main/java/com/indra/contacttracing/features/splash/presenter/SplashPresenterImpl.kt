package com.indra.contacttracing.features.splash.presenter

import android.os.Handler
import com.indra.contacttracing.features.splash.protocols.SplashPresenter
import com.indra.contacttracing.features.splash.protocols.SplashRouter
import com.indra.contacttracing.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter
) : SplashPresenter {

    override fun viewReady() {
        Handler().postDelayed({
            router.navigateToOnboarding()
        }, 2000)
    }

}