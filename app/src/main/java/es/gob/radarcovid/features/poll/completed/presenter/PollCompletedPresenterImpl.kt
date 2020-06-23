package es.gob.radarcovid.features.poll.completed.presenter

import es.gob.radarcovid.features.poll.completed.protocols.PollCompletedPresenter
import es.gob.radarcovid.features.poll.completed.protocols.PollCompletedView
import javax.inject.Inject

class PollCompletedPresenterImpl @Inject constructor(private val view: PollCompletedView) :
    PollCompletedPresenter {

    override fun viewReady() {

    }

    override fun onMailButtonClick() {
        view.sendMailToSupport()
    }

    override fun onContactSupportButtonClick() {
        view.showDialerForSupport()
    }

    override fun onBackToMainButtonClick() {
        view.finish()
    }

}