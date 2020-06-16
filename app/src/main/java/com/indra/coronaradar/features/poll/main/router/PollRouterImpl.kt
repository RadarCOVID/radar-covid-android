package com.indra.coronaradar.features.poll.main.router

import android.content.Context
import com.indra.coronaradar.features.poll.completed.PollCompletedActivity
import com.indra.coronaradar.features.poll.main.protocols.PollRouter
import javax.inject.Inject

class PollRouterImpl @Inject constructor(private val context: Context) : PollRouter {

    override fun navigateToPollCompleted() {
        PollCompletedActivity.open(context)
    }

}