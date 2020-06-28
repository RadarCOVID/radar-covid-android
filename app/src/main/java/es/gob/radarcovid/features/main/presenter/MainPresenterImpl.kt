package es.gob.radarcovid.features.main.presenter

import es.gob.radarcovid.datamanager.usecase.MainUseCase
import es.gob.radarcovid.features.main.protocols.MainPresenter
import es.gob.radarcovid.features.main.protocols.MainRouter
import es.gob.radarcovid.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val mainUseCase: MainUseCase
) : MainPresenter {

    override fun viewReady(activateRadar: Boolean) {

        router.navigateToHome(activateRadar)

    }

    override fun onResume() {
        /* DON'T REMOVE. IF YOU REMOVE THE MANUAL SYNC, THE METHOD onExposureStatusChange
        WON'T BE TRIGGERED WHEN onResume IS EXECUTED */
        mainUseCase.syncExposureData()
    }

    override fun onHomeButtonClick() {
        router.navigateToHome(false)
    }

    override fun onHealthButtonClick() {
        router.navigateToHealth()
    }

    override fun onProfileButtonClick() {
        router.navigateToProfile()
    }

    override fun onHelplineButtonClick() {
        if (mainUseCase.isPollCompleted())
            router.navigateToPollCompleted()
        else
            router.navigateToHelpline()
    }

    override fun onPollCompleted() {
        router.navigateToPollCompleted()

    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

}