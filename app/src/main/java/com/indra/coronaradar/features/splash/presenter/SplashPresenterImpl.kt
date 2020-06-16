package com.indra.coronaradar.features.splash.presenter

import android.os.Handler
import com.indra.coronaradar.datamanager.usecase.CheckGaenUseCase
import com.indra.coronaradar.datamanager.usecase.GetInternetInfoUseCase
import com.indra.coronaradar.datamanager.usecase.GetUuidUseCase
import com.indra.coronaradar.datamanager.usecase.OnboardingCompletedUseCase
import com.indra.coronaradar.features.splash.protocols.SplashPresenter
import com.indra.coronaradar.features.splash.protocols.SplashRouter
import com.indra.coronaradar.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter,
    private val useCaseInternetInfo: GetInternetInfoUseCase,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val checkGaenUseCase: CheckGaenUseCase,
    private val getUuidUseCase: GetUuidUseCase
) : SplashPresenter {

    private var isWaitingToStart: Boolean = false

    override fun viewReady() {
        if (!getUuidUseCase.isUuidInitialized())
            requestUuid()
    }

    override fun onResume() {
        if (getUuidUseCase.isUuidInitialized()) {
            if (!useCaseInternetInfo.isInternetAvailable()) {
                view.showNoInternetWarning()
            } else {
                checkGaenUseCase.checkGaenAvailability { available ->
                    if (available)
                        navigateToHomeWithDelay()
                    else
                        view.showPlayServicesRequiredDialog()
                }
            }
        }
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

    override fun onInstallPlayServicesButtonClick() {
        router.navigateToPlayServicesPage()
    }

    private fun requestUuid() {
        getUuidUseCase.getUuid(onSuccess = { uuid ->
            getUuidUseCase.persistUuid(uuid)
            onResume()
        }, onError = {

        })
    }

    private fun navigateToHomeWithDelay() {
        if (!isWaitingToStart) {
            isWaitingToStart = true
            Handler().postDelayed({
                if (onboardingCompletedUseCase.isOnBoardingCompleted())
                    router.navigateToMain()
                else
                    router.navigateToOnboarding()
            }, 2000)
        }
    }

}