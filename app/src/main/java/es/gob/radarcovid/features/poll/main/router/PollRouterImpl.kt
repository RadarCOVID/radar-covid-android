package es.gob.radarcovid.features.poll.main.router

import android.app.Activity
import android.content.Context
import es.gob.radarcovid.features.poll.main.protocols.PollRouter
import javax.inject.Inject

class PollRouterImpl @Inject constructor(private val context: Context) : PollRouter {

    override fun navigateToPollCompleted() {
        with(context as Activity) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

}