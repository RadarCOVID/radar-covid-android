package es.gob.covidradar.features.onboarding.presenter

import es.gob.covidradar.features.onboarding.protocols.OnboardingPresenter
import es.gob.covidradar.features.onboarding.protocols.OnboardingRouter
import es.gob.covidradar.features.onboarding.protocols.OnboardingView
import javax.inject.Inject

class OnboardingPresenterImpl @Inject constructor(
    private val view: OnboardingView,
    private val router: OnboardingRouter
) : OnboardingPresenter {

    override fun viewReady() {

    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onBackButtonPressed(isFirstItem: Boolean) {
        if (isFirstItem)
            view.showExitConfirmationDialog()
        else
            view.showPreviousPage()
    }

    override fun onContinueButtonClick() {
        view.showNextPage()
    }

    override fun onFinishButtonClick(activateRadar: Boolean) {
        if (activateRadar) {
            if (view.isBluetoothEnabled()) {
                router.navigateToMain(true)
                view.finish()
            } else {
                view.showBluetoothRequest()
            }
        } else {
            router.navigateToMain(false)
            view.finish()
        }
    }

    override fun onBluetoothEnabled() {
        router.navigateToMain(true)
        view.finish()
    }

}