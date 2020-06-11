package com.indra.coronaradar.features.splash.protocols

interface SplashView {

    fun showNoInternetWarning()

    fun showPlayServicesRequiredDialog()

    fun finish()

}

interface SplashPresenter {

    fun viewReady()

    fun onResume()

    fun onNetworkRetryButtonClick()

    fun onNetworkDialogCloseButtonClick()

    fun onInstallPlayServicesButtonClick()

}

interface SplashRouter {

    fun navigateToPlayServicesPage()

    fun navigateToLegalInfo()

    fun navigateToOnboarding()

    fun navigateToMain()

}