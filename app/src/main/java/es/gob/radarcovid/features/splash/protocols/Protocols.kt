package es.gob.radarcovid.features.splash.protocols

import es.gob.radarcovid.common.view.RequestView

interface SplashView : RequestView {

    fun showNoInternetWarning()

    fun showPlayServicesRequiredDialog()

    fun showNeedUpdateDialog()

    fun reloadLabels()

    fun finish()

}

interface SplashPresenter {

    fun viewReady()

    fun onResume()

    fun onNetworkRetryButtonClick()

    fun onNetworkDialogCloseButtonClick()

    fun onInstallPlayServicesButtonClick()

    fun onUpdateAppButtonClick()

}

interface SplashRouter {

    fun navigateToPlayServicesPage()

    fun navigateToOnboarding()

    fun navigateToMain()

    fun navigateToPlayStore()

}