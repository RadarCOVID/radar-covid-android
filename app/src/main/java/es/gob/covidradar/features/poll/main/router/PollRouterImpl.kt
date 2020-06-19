package es.gob.covidradar.features.poll.main.router

import android.content.Context
import es.gob.covidradar.features.poll.completed.view.PollCompletedActivity
import es.gob.covidradar.features.poll.main.protocols.PollRouter
import javax.inject.Inject

class PollRouterImpl @Inject constructor(private val context: Context) : PollRouter {

    override fun navigateToPollCompleted() {
        PollCompletedActivity.open(context)
    }

}