package com.indra.coronaradar.features.exposure.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseBackNavigationActivity
import com.indra.coronaradar.features.exposure.protocols.ExposurePresenter
import com.indra.coronaradar.features.exposure.protocols.ExposureView
import kotlinx.android.synthetic.main.activity_exposure.*
import javax.inject.Inject

class ExposureActivity : BaseBackNavigationActivity(), ExposureView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ExposureActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: ExposurePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exposure)

        initViews()
        presenter.viewReady()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun initViews() {

    }

    override fun showExpositionLevelLow() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_low)
        textViewExpositionDetailTitle.setText(R.string.exposure_detail_low)
        textViewExpositionDetailMessage.setText(R.string.exposure_detail_message_low)

        wrapperExposureLow.visibility = View.VISIBLE
        wrapperExposureHigh.visibility = View.GONE
    }

    override fun showExpositionLevelMedium() {
//        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_medium)
//        textViewExpositionDetailTitle.setText(R.string.exposure_detail_medium)
//        textViewExpositionDetailMessage.setText(R.string.exposure_detail_message_medium)
//
//        textViewTipsSubtitle.setText(R.string.exposure_detail_tips_subtitle_medium)
//        textViewTipsSubtitle.setTextColor(ContextCompat.getColor(this, R.color.orange))
//
//        wrapperTipsAndIndications.visibility = View.VISIBLE
//        textViewReportButtonMessage.visibility = View.VISIBLE
//        buttonCovidReport.visibility = View.VISIBLE
    }

    override fun showExpositionLevelHigh() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_high)
        textViewExpositionDetailTitle.setText(R.string.exposure_detail_high)
        textViewExpositionDetailMessage.setText(R.string.exposure_detail_message_high)

        wrapperExposureHigh.visibility = View.VISIBLE
        wrapperExposureLow.visibility = View.GONE
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
                    R.string.exposure_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.days, daysElapsed, daysElapsed)
                )

            hoursElapsed > 0 ->
                getString(
                    R.string.exposure_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.hours, hoursElapsed, hoursElapsed)
                )
            else ->
                getString(
                    R.string.exposure_detail_last_update,
                    date,
                    resources.getQuantityString(R.plurals.minutes, minutesElapsed, minutesElapsed)
                )
        }
        textViewExpositionLastUpdate.text = text
    }

}
