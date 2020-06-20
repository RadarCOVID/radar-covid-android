package es.gob.covidradar.features.helpline.protocols

interface HelplineView {

    fun showDialerForSupport()

    fun sendMailToInterview()

}

interface HelplinePresenter {

    fun viewReady()

    fun onStartButtonClick()

    fun onContactSupportButtonClick()

    fun onInterViewEmailButtonClick()

}

interface HelplineRouter {

    fun navigateToBrowser(url: String)

    fun navigateToPoll()

}