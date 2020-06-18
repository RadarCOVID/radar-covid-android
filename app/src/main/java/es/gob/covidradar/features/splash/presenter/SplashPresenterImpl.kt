package es.gob.covidradar.features.splash.presenter

import android.os.Handler
import es.gob.covidradar.datamanager.usecase.CheckGaenUseCase
import es.gob.covidradar.datamanager.usecase.GetInternetInfoUseCase
import es.gob.covidradar.datamanager.usecase.GetUuidUseCase
import es.gob.covidradar.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.covidradar.features.splash.protocols.SplashPresenter
import es.gob.covidradar.features.splash.protocols.SplashRouter
import es.gob.covidradar.features.splash.protocols.SplashView
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