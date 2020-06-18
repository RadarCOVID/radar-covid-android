package es.gob.covidradar.features.poll.completed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.gob.covidradar.R
import kotlinx.android.synthetic.main.activity_poll_completed.*


class PollCompletedActivity : AppCompatActivity() {

    companion object {

        fun open(context: Context) =
            context.startActivity(Intent(context, PollCompletedActivity::class.java))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll_completed)

        initViews()
    }

    private fun initViews() {
        textViewEmail.setOnClickListener { sendMailTo(getString(R.string.poll_completed_email)) }
        buttonMain.setOnClickListener { finish() }
    }


    private fun sendMailTo(to: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Text")
        }

        startActivity(
            Intent.createChooser(
                emailIntent,
                "Send mail..."
            )
        )
    }
}
