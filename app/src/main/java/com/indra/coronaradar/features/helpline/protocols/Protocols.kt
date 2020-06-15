package com.indra.coronaradar.features.helpline.protocols

interface HelplineView {

}

interface HelplinePresenter {

    fun viewReady()

    fun onStartButtonClick()

}

interface HelplineRouter {

    fun navigateToBrowser(url:String)

    fun navigateToPoll()

}