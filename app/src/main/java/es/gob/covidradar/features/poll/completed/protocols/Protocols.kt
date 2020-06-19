package es.gob.covidradar.features.poll.completed.protocols

interface PollCompletedView {

    fun finish()

}

interface PollCompletedPresenter {

    fun viewReady()

}