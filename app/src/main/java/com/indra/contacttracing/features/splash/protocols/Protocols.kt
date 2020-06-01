package com.indra.contacttracing.features.splash.protocols

interface SplashView {

    fun showNoInternetWarning()

    fun finish()

}

interface SplashPresenter {

    fun viewReady()

    fun onNetworkRetryButtonClick()

    fun onNetworkDialogCloseButtonClick()

}

interface SplashRouter {

    fun navigateToLegalInfo()

    fun navigateToOnboarding()

    fun navigateToMain()

}