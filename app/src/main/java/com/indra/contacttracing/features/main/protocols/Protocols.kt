package com.indra.contacttracing.features.main.protocols

import com.indra.contacttracing.common.view.RequestView

interface MainView : RequestView {

}

interface MainPresenter {

    fun viewReady()

    fun onHomeButtonClick()

    fun onHealthButtonClick()

    fun onProfileButtonClick()

    fun onHelplineButtonClick()

}

interface MainRouter {

    fun navigateToHome()

    fun navigateToHealth()

    fun navigateToProfile()

    fun navigateToHelpline()

}