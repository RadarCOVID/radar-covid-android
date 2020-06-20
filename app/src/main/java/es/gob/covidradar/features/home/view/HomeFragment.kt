package es.gob.covidradar.features.home.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import es.gob.covidradar.R
import es.gob.covidradar.common.base.BaseFragment
import es.gob.covidradar.common.view.CMDialog
import es.gob.covidradar.features.home.protocols.HomePresenter
import es.gob.covidradar.features.home.protocols.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

    companion object {

        private const val ARG_ACTIVATE_RADAR = "arg_activate_radar"

        fun newInstance(activateRadar: Boolean) = HomeFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_ACTIVATE_RADAR, activateRadar)
            }
        }

    }

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        presenter.viewReady(arguments?.getBoolean(ARG_ACTIVATE_RADAR) ?: false)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun initViews() {
        switchRadar.setOnClickListener {
            if (!switchRadar.isChecked) { // The status is already change when onclick is executed
                switchRadar.isChecked = true
                switchRadar.jumpDrawablesToCurrentState();
                CMDialog.createDialog(
                    context!!, R.string.radar_warning_title,
                    R.string.radar_warning_message,
                    R.string.radar_warning_button, null
                ) {
                    presenter.onSwitchRadarClick(true)
                }.show()
            } else {
                switchRadar.isChecked = false
                switchRadar.jumpDrawablesToCurrentState();
                presenter.onSwitchRadarClick(false)
            }
        }

        wrapperExposition.setOnClickListener { presenter.onExpositionBlockClick() }
        buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
    }

    override fun showExpositionLevelLow() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_low)
        textViewExpositionTitle.setText(R.string.exposition_block_low_title)
        textViewExpositionDescription.setText(R.string.exposition_block_low_description)
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.green))
    }

    override fun showExpositionLevelMedium() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_medium)
        textViewExpositionTitle.setText(R.string.exposition_block_medium_title)
        textViewExpositionDescription.setText(R.string.exposition_block_medium_description)
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.orange))
    }

    override fun showExpositionLevelHigh() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_high)
        textViewExpositionTitle.setText(R.string.exposition_block_high_title)
        textViewExpositionDescription.setText(R.string.exposition_block_medium_description)
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
    }

    override fun setLastUpdateTime(
        daysElapsed: Int,
        hoursElapsed: Int,
        minutesElapsed: Int
    ) {
        textViewExpositionLastUpdate.visibility = View.VISIBLE
        when {
            daysElapsed > 0 ->
                textViewExpositionLastUpdate.text =
                    resources.getQuantityString(R.plurals.days, daysElapsed, daysElapsed)
            hoursElapsed > 0 ->
                textViewExpositionLastUpdate.text =
                    resources.getQuantityString(R.plurals.hours, hoursElapsed, hoursElapsed)
            else ->
                textViewExpositionLastUpdate.text =
                    resources.getQuantityString(R.plurals.minutes, minutesElapsed, minutesElapsed)
        }
    }

    override fun setRadarBlockChecked(isChecked: Boolean) {
        switchRadar.isChecked = isChecked
        showRadarBlockEnabled(isChecked)
    }

    private fun showRadarBlockEnabled(enabled: Boolean) {
        if (enabled) {
            textViewRadarTitle.setText(R.string.radar_block_enabled_title)
            textViewRadarDescription.setText(R.string.radar_block_enabled_description)
            textViewRadarDescription.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_light),
                Typeface.NORMAL
            )
        } else {
            textViewRadarTitle.setText(R.string.radar_block_disabled_title)
            textViewRadarDescription.setText(R.string.radar_block_disabled_description)
            textViewRadarDescription.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            textViewRadarDescription.setTypeface(
                ResourcesCompat.getFont(context!!, R.font.muli_bold),
                Typeface.NORMAL
            )
        }
    }
}