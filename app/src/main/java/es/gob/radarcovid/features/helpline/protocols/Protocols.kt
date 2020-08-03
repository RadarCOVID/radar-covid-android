package es.gob.radarcovid.features.helpline.protocols

interface HelplineView {

    fun showDialerForSupport()

    fun sendMailToInterview()

}

interface HelplinePresenter {

    fun viewReady()

    fun onStartButtonClick()

    fun onContactSupportButtonClick()

    fun onInterViewEmailButtonClick()

    fun onUrlButtonClick(url: String)

}

interface HelplineRouter {

    fun navigateToBrowser(url: String)

    fun navigateToPoll()

}