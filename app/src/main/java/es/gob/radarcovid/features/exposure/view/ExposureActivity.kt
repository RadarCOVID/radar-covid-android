package es.gob.radarcovid.features.exposure.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.features.exposure.protocols.ExposurePresenter
import es.gob.radarcovid.features.exposure.protocols.ExposureView
import kotlinx.android.synthetic.main.activity_exposure.*
import kotlinx.android.synthetic.main.layout_exposure_detail_high.*
import kotlinx.android.synthetic.main.layout_exposure_detail_low.*
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

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun initViews() {
        wrapperContactButton.setOnClickListener { presenter.onContactButtonClick() }
        buttonMoreInfoLow.setOnClickListener { presenter.onMoreInfoButtonClick() }
        buttonMoreInfoHigh.setOnClickListener { presenter.onMoreInfoButtonClick() }
    }

    override fun showExpositionLevelLow() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_low)
        textViewExpositionDetailTitleSmall.setText(R.string.exposure_detail_low_title_small)
        textViewExpositionDetailTitle.setText(R.string.exposure_detail_low_title)

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
        textViewExpositionDetailTitleSmall.setText(R.string.exposure_detail_high_title_small)
        textViewExpositionDetailTitle.setText(R.string.exposure_detail_high_title)

        wrapperExposureHigh.visibility = View.VISIBLE
        wrapperExposureLow.visibility = View.GONE
    }

    override fun setUpdateAndExposureDates(
        date: String,
        daysElapsed: Int?,
        hoursElapsed: Int?,
        minutesElapsed: Int?
    ) {

        var text = ""
        if (daysElapsed != null && hoursElapsed != null && minutesElapsed != null) {
            text = when {
                daysElapsed > 0 -> {
                    val daysText =
                        resources.getQuantityString(R.plurals.days, daysElapsed, daysElapsed)
                    getString(R.string.exposure_detail_high_last_update, daysText, date)
                }
                hoursElapsed > 0 -> {
                    val hoursText =
                        resources.getQuantityString(R.plurals.hours, hoursElapsed, hoursElapsed)
                    getString(R.string.exposure_detail_high_last_update, hoursText, date)
                }
                else -> {
                    val minutesText =
                        resources.getQuantityString(
                            R.plurals.minutes,
                            minutesElapsed,
                            minutesElapsed
                        )
                    getString(R.string.exposure_detail_high_last_update, minutesText, date)
                }
            }
        } else {
            text = getString(R.string.exposure_detail_low_last_update, date)
        }

        textViewExpositionLastUpdate.text = text
    }

    override fun setLastUpdateNoData() {
        textViewExpositionLastUpdate.setText(R.string.exposure_detail_low_last_update_no_data)
    }

    override fun showDialerForSupport() {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${getString(R.string.contact_support_phone)}")
        })
    }

}
