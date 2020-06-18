package es.gob.covidradar.features.main.presenter

import es.gob.covidradar.datamanager.usecase.SyncExposureDataUseCase
import es.gob.covidradar.features.main.protocols.MainPresenter
import es.gob.covidradar.features.main.protocols.MainRouter
import es.gob.covidradar.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val syncExposureDataUseCase: SyncExposureDataUseCase
) : MainPresenter {

    override fun viewReady(activateRadar: Boolean) {

        router.navigateToHome(activateRadar)

    }

    override fun onResume() {
        syncExposureDataUseCase.syncExposureData()
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
        router.navigateToHelpline()
    }

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

}