package es.gob.radarcovid.features.home.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.home.protocols.HomePresenter
import es.gob.radarcovid.features.home.protocols.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeView {

    companion object {

        private const val REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS = 1

        private const val ARG_ACTIVATE_RADAR = "arg_activate_radar"

        fun newInstance(activateRadar: Boolean) = HomeFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_ACTIVATE_RADAR, activateRadar)
            }
        }

    }

    @Inject
    lateinit var presenter: HomePresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS && resultCode == Activity.RESULT_OK)
            presenter.onBatteryOptimizationsIgnored()
    }

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

    override fun onPause() {
        super.onPause()
        presenter.onPause()
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
        switchRadar.setOnTouchListener { _, event ->
            event.actionMasked == MotionEvent.ACTION_MOVE
        }

        wrapperExposition.setOnClickListener { presenter.onExpositionBlockClick() }
        buttonCovidReport.setOnClickListener { presenter.onReportButtonClick() }
    }

    override fun showInitializationCheckAnimation() {
        imageViewBackgroundLogo.alpha = 0f
        imageViewInitializationCheck.alpha = 1f
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(imageViewBackgroundLogo, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(imageViewInitializationCheck, View.ALPHA, 1f, 0f)
            )
            duration = 2000 //set duration for animations
            startDelay = 2000
        }.start()
    }

    override fun showExpositionLevelLow() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_low)
        textViewExpositionTitle.setText(R.string.exposition_block_low_title)
        textViewExpositionDescriptionL1.setText(R.string.exposition_block_low_description_l1)
        textViewExpositionDescriptionL2.setText(R.string.exposition_block_low_description_l2)
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.green))
    }

    override fun showExpositionLevelHigh() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_high)
        textViewExpositionTitle.setText(R.string.exposition_block_high_title)
        textViewExpositionDescriptionL1.setText(R.string.exposition_block_medium_description_l1)
        textViewExpositionDescriptionL2.setText(R.string.exposition_block_medium_description_l2)
        textViewExpositionTitle.setTextColor(ContextCompat.getColor(context!!, R.color.red))
    }

    override fun showExpositionLevelInfected() {
        wrapperExposition.setBackgroundResource(R.drawable.background_shape_exposition_high)
        textViewExpositionTitle.setText(R.string.exposition_block_infected_title)
        textViewExpositionDescriptionL1.setText(R.string.exposition_block_infected_description_l1)
        textViewExpositionDescriptionL2.setText(R.string.exposition_block_infected_description_l2)
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

    override fun areBatteryOptimizationsIgnored(): Boolean {
        (context!!.getSystemService(POWER_SERVICE) as PowerManager).let { powerManager ->
            return powerManager.isIgnoringBatteryOptimizations(context!!.packageName)
        }
    }

    override fun requestIgnoreBatteryOptimizations() {
        (context!!.getSystemService(POWER_SERVICE) as PowerManager?)?.let {
            if (!it.isIgnoringBatteryOptimizations(activity!!.packageName)) {

                startActivityForResult(Intent().apply {
                    action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    //action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                    data = Uri.parse("package:${activity!!.packageName}")
                }, REQUEST_CODE_IGNORE_BATTERY_OPTIMIZATIONS)
            }
        }
    }

    override fun showUnableToReportCovidDialog() {
        CMDialog.createDialog(
            context!!, R.string.empty_text,
            R.string.covid_report_warning,
            R.string.accept, null
        ) {}.show()
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