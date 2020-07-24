package es.gob.radarcovid.features.poll.completed.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.poll.completed.protocols.PollCompletedPresenter
import es.gob.radarcovid.features.poll.completed.protocols.PollCompletedView
import kotlinx.android.synthetic.main.activity_poll_completed.*
import javax.inject.Inject


class PollCompletedFragment : BaseFragment(), PollCompletedView {

    companion object {

        fun newInstance() = PollCompletedFragment()

    }

    @Inject
    lateinit var presenter: PollCompletedPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_poll_completed, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        textViewEmail.setOnClickListener { presenter.onMailButtonClick() }
        buttonContactSupport.setOnClickListener { presenter.onContactSupportButtonClick() }
    }


    override fun sendMailToSupport() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(labelManager.getText("CONTACT_EMAIL", R.string.contact_email)).toString()
            )
//            putExtra(Intent.EXTRA_SUBJECT, "Subject")
//            putExtra(Intent.EXTRA_TEXT, "Text")
        }

        startActivity(
            Intent.createChooser(
                emailIntent,
                "Send mail..."
            )
        )
    }

    override fun showDialerForSupport() {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse(
                "tel:${labelManager.getText(
                    "CONTACT_PHONE",
                    R.string.contact_support_phone
                )}"
            )
        })
    }

}
