package es.gob.covidradar.features.helpline.protocols

interface HelplineView {

    fun showDialerForSupport()

}

interface HelplinePresenter {

    fun viewReady()

    fun onStartButtonClick()

    fun onContactSupportButtonClick()

}

interface HelplineRouter {

    fun navigateToBrowser(url: String)

    fun navigateToPoll()

}