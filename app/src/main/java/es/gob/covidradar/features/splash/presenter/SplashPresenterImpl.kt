package es.gob.covidradar.features.splash.presenter

import android.os.Handler
import es.gob.covidradar.datamanager.usecase.GetInternetInfoUseCase
import es.gob.covidradar.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.covidradar.datamanager.usecase.SplashUseCase
import es.gob.covidradar.features.splash.protocols.SplashPresenter
import es.gob.covidradar.features.splash.protocols.SplashRouter
import es.gob.covidradar.features.splash.protocols.SplashView
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter,
    private val useCaseInternetInfo: GetInternetInfoUseCase,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val splashUseCase: SplashUseCase
) : SplashPresenter {

    private var isWaitingToStart: Boolean = false

    override fun viewReady() {
        requestInitialization()
    }

    override fun onResume() {
        if (!useCaseInternetInfo.isInternetAvailable()) {
            view.showNoInternetWarning()
        } else if (splashUseCase.isUuidInitialized()) {
            splashUseCase.checkGaenAvailability { available ->
                if (available)
                    navigateToHomeWithDelay()
                else
                    view.showPlayServicesRequiredDialog()
            }
        }
    }

    override fun onNetworkRetryButtonClick() {
        requestInitialization()
    }

    override fun onNetworkDialogCloseButtonClick() {
        view.finish()
    }

    override fun onInstallPlayServicesButtonClick() {
        router.navigateToPlayServicesPage()
    }

    private fun requestInitialization() {
        splashUseCase.getInitializationObservable()
            .subscribe(
                {
                    splashUseCase.updateTracingSettings(it.first)
                    if (it.second.isNotEmpty()) {
                        splashUseCase.persistUuid(it.second)
                        onResume()
                    }
                },
                { onResume() }
            )
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