package es.gob.radarcovid.features.helpline.presenter

import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import javax.inject.Inject

class HelplinePresenterImpl @Inject constructor(
    private val view: HelplineView,
    private val router: HelplineRouter
) : HelplinePresenter {

    override fun viewReady() {

    }

    override fun onStartButtonClick() {
        //router.navigateToBrowser("http://www.google.com")
        router.navigateToPoll()
    }

    override fun onContactSupportButtonClick() {
        view.showDialerForSupport()
    }

    override fun onInterViewEmailButtonClick() {
        view.sendMailToInterview()
    }

}