package es.gob.radarcovid.features.poll.completed.protocols

interface PollCompletedView {

    fun sendMailToSupport()

    fun showDialerForSupport()

}

interface PollCompletedPresenter {

    fun viewReady()

    fun onMailButtonClick()

    fun onContactSupportButtonClick()

}