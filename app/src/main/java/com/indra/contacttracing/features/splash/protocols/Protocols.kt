package com.indra.contacttracing.features.splash.protocols

interface SplashView {

}

interface SplashPresenter {

    fun viewReady()

}

interface SplashRouter {

    fun navigateToOnboarding()

    fun navigateToMain()

}