package es.gob.radarcovid.features.splash.presenter

import android.os.Handler
import es.gob.radarcovid.datamanager.usecase.GetInternetInfoUseCase
import es.gob.radarcovid.datamanager.usecase.OnboardingCompletedUseCase
import es.gob.radarcovid.datamanager.usecase.SplashUseCase
import es.gob.radarcovid.features.splash.protocols.SplashPresenter
import es.gob.radarcovid.features.splash.protocols.SplashRouter
import es.gob.radarcovid.features.splash.protocols.SplashView
import es.gob.radarcovid.models.domain.InitializationData
import java.net.UnknownHostException
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(
    private val view: SplashView,
    private val router: SplashRouter,
    private val useCaseInternetInfo: GetInternetInfoUseCase,
    private val onboardingCompletedUseCase: OnboardingCompletedUseCase,
    private val splashUseCase: SplashUseCase
) : SplashPresenter {

    private var isWaitingToStart: Boolean = false
    private var isInitializationCompleted: Boolean = false

    override fun viewReady() {
        requestInitialization()
    }

    override fun onResume() {
        if (isInitializationCompleted) {
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

    override fun onUpdateAppButtonClick() {
        router.navigateToPlayStore()
        view.finish()
    }

    private fun requestInitialization() {
        splashUseCase.getInitializationObservable()
            .subscribe(
                { onInitializationSuccess(it) },
                { onInitializationError(it) }
            )
    }

    private fun onInitializationSuccess(initializationData: InitializationData) {
        val versionCode = splashUseCase.getVersionCode()
        val settings = initializationData.settings

        if (initializationData.labels.isNotEmpty()) {
            splashUseCase.persistLabels(initializationData.labels)
            view.reloadLabels()
        }

        if (versionCode < settings.appInfo.minVersionCode) {
            view.showNeedUpdateDialog()
        } else {
            splashUseCase.updateTracingSettings(settings)
            if (initializationData.uuid.isNotEmpty())
                splashUseCase.persistUuid(initializationData.uuid)
            isInitializationCompleted = true
            onResume()
        }
    }

    private fun onInitializationError(error: Throwable) {
        if (error !is UnknownHostException) { // IT'S NOT A "NO INTERNET" ERROR
            view.showError(error, true)
        } else {
            isInitializationCompleted = true
            onResume()
        }
    }

    private fun navigateToHomeWithDelay() {
        if (!isWaitingToStart) {
            isWaitingToStart = true
            Handler().postDelayed({
                if (onboardingCompletedUseCase.isOnBoardingCompleted())
                    router.navigateToMain()
                else
                    router.navigateToOnboarding()
                view.finish()
            }, 2000)
        }
    }

}