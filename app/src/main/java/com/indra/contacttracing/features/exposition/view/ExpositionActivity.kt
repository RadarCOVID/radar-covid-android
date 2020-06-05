package com.indra.contacttracing.features.exposition.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseBackNavigationActivity
import com.indra.contacttracing.features.exposition.protocols.ExpositionPresenter
import com.indra.contacttracing.features.exposition.protocols.ExpositionView
import kotlinx.android.synthetic.main.activity_exposition.*
import javax.inject.Inject

class ExpositionActivity : BaseBackNavigationActivity(), ExpositionView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ExpositionActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: ExpositionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exposition)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
    }

    override fun showExpositionLevelLow() {
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_low)
        textViewExpositionDetailTitle.setTextColor(ContextCompat.getColor(this, R.color.green))
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_low)
        wrapperReportButton.visibility = View.GONE
    }

    override fun showExpositionLevelMedium() {
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_medium)
        textViewExpositionDetailTitle.setTextColor(ContextCompat.getColor(this, R.color.orange))
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_medium)
        wrapperReportButton.visibility = View.VISIBLE
    }

    override fun showExpositionLevelHigh() {
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_high)
        textViewExpositionDetailTitle.setTextColor(ContextCompat.getColor(this, R.color.red))
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_high)
        wrapperReportButton.visibility = View.VISIBLE
    }

    override fun setLastUpdateTime(text: String) {
        textViewExpositionLastUpdate.text = text
    }

}
