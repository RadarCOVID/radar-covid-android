package es.gob.radarcovid.features.main.protocols

import es.gob.radarcovid.common.view.RequestView

interface MainView : RequestView {

    fun showExitConfirmationDialog()

    fun finish()

}

interface MainPresenter {

    fun viewReady(activateRadar: Boolean)

    fun onResume()

    fun onHomeButtonClick()

    fun onHealthButtonClick()

    fun onProfileButtonClick()

    fun onHelplineButtonClick()

    fun onBackPressed()

    fun onExitConfirmed()

}

interface MainRouter {

    fun navigateToHome(activateRadar: Boolean)

    fun navigateToHealth()

    fun navigateToProfile()

    fun navigateToHelpline()

}