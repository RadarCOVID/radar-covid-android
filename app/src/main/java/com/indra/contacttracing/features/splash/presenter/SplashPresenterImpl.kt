package com.indra.contacttracing.features.splash.presenter

import android.os.Handler
import com.indra.contacttracing.datamanager.usecase.GetInternetInfoUseCase
import com.indra.contacttracing.datamanager.usecase.OnboardingCompletedUseCase
import com.indra.contacttracing.features.splash.protocols.SplashPresenter
import com.indra.contacttracing.features.splash.protocols.SplashRouter
import com.indra.contacttracing.features.splash.protocols.SplashView
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