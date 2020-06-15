package com.indra.coronaradar.features.poll.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseActivity
import com.indra.coronaradar.features.poll.protocols.PollPresenter
import com.indra.coronaradar.features.poll.protocols.PollView
import kotlinx.android.synthetic.main.activity_poll.*
import javax.inject.Inject

class PollActivity : BaseActivity(), PollView {

    companion object {

        fun open(context: Context) =
            context.startActivity(Intent(context, PollActivity::class.java))

    }

    @Inject
    lateinit var presenter: PollPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        stepIndicator.setProgress(10, 11)
    }
}
