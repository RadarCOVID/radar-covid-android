package com.indra.coronaradar.features.exposure.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseBackNavigationActivity
import com.indra.coronaradar.features.exposure.protocols.ExpositionPresenter
import com.indra.coronaradar.features.exposure.protocols.ExpositionView
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

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun initViews() {
        buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
    }

    override fun showExpositionLevelLow() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_low)
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_low)
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_low)

        textViewTipsSubtitle.setText(R.string.exposition_detail_tips_subtitle_low)
        textViewTipsSubtitle.setTextColor(ContextCompat.getColor(this, R.color.green))

        wrapperTipsAndAcknowledge.visibility = View.VISIBLE
    }

    override fun showExpositionLevelMedium() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_medium)
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_medium)
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_medium)

        textViewTipsSubtitle.setText(R.string.exposition_detail_tips_subtitle_medium)
        textViewTipsSubtitle.setTextColor(ContextCompat.getColor(this, R.color.orange))

        wrapperTipsAndIndications.visibility = View.VISIBLE
        textViewReportButtonMessage.visibility = View.VISIBLE
        buttonCovidReport.visibility = View.VISIBLE
    }

    override fun showExpositionLevelHigh() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_high)
        textViewExpositionDetailTitle.setText(R.string.exposition_detail_high)
        textViewExpositionDetailMessage.setText(R.string.exposition_detail_message_high)

        textViewTipsSubtitle.setText(R.string.exposition_detail_tips_subtitle_high)
        textViewTipsSubtitle.setTextColor(ContextCompat.getColor(this, R.color.red))

        wrapperTipsAndIndications.visibility = View.VISIBLE
        buttonCovidReport.visibility = View.VISIBLE
    }

    override fun setLastUpdateTime(
        date: String,
        daysElapsed: Int,
        hoursElapsed: Int,
        minutesElapsed: Int
    ) {
        val text = when {
            daysElapsed > 0 ->
                getString(
                    R.string.exposition_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.days, daysElapsed, daysElapsed)
                )

            hoursElapsed > 0 ->
                getString(
                    R.string.exposition_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.hours, hoursElapsed, hoursElapsed)
                )
            else ->
                getString(
                    R.string.exposition_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.minutes, minutesElapsed, minutesElapsed)
                )
        }
        textViewExpositionLastUpdate.text = text
    }

}