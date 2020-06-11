package com.indra.coronaradar.features.splash.presenter

import android.os.Handler
import com.indra.coronaradar.datamanager.usecase.GetInternetInfoUseCase
import com.indra.coronaradar.datamanager.usecase.OnboardingCompletedUseCase
import com.indra.coronaradar.features.splash.protocols.SplashPresenter
import com.indra.coronaradar.features.splash.protocols.SplashRouter
import com.indra.coronaradar.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter,
    private val useCaseInternetInfo: GetInternetInfoUseCase,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase
) : SplashPresenter {

    override fun viewReady() {
        onNetworkRetryButtonClick()
    }

    override fun onNetworkRetryButtonClick() {
        if (!useCaseInternetInfo.isInternetAvailable())
            view.showNoInternetWarning()
        else
            navigateToHomeWithDelay()
    }

    override fun onNetworkDialogCloseButtonClick() {
        view.finish()
    }

    private fun navigateToHomeWithDelay() {
        Handler().postDelayed({
            if (onboardingCompletedUseCase.isOnBoardingCompleted())
                router.navigateToMain()
            else
                router.navigateToOnboarding()
        }, 2000)
    }

}