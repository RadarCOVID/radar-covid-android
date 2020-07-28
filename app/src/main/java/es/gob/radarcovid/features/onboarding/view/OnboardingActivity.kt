package es.gob.radarcovid.features.onboarding.view

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseActivity
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment
import es.gob.radarcovid.features.onboarding.pages.OnboardingStepPageFragment
import es.gob.radarcovid.features.onboarding.pages.legal.view.LegalInfoFragment
import es.gob.radarcovid.features.onboarding.protocols.OnboardingPresenter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingView
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

class OnboardingActivity : BaseActivity(), OnboardingView, OnboardingStepPageFragment.Callback,
    LocaleSelectionFragment.Callback {

    companion object {

        private const val REQUEST_CODE_BLUETOOTH: Int = 1

    }

    @Inject
    lateinit var presenter: OnboardingPresenter

    var onLocaleSelectedListener: (() -> Unit)? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BLUETOOTH && resultCode == Activity.RESULT_OK)
            presenter.onBluetoothEnabled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        viewPager.adapter = OnboardingAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 4
    }

    override fun onBackPressed() {
        presenter.onBackButtonPressed(viewPager.currentItem == 0)
    }

    override fun onContinueButtonClick() {
        presenter.onContinueButtonClick()
    }

    override fun onFinishButtonClick(activateRadar: Boolean) {
        presenter.onFinishButtonClick(activateRadar)
    }

    override fun showPreviousPage() {
        viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    override fun showNextPage() {
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }

    override fun isBluetoothEnabled(): Boolean =
        BluetoothAdapter.getDefaultAdapter()?.isEnabled ?: false

    override fun showBluetoothRequest() {
        startActivityForResult(
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
            REQUEST_CODE_BLUETOOTH
        )
    }

    override fun showExitConfirmationDialog() {
        CMDialog.createDialog(
            this, "",
            getString(R.string.warning_exit_application_message),
            getString(R.string.warning_exit_application_button), null
        ) {
            presenter.onExitConfirmed()
        }.show()
    }

    private class OnboardingAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val totalPages = 3

        override fun getItemCount(): Int = totalPages

        override fun createFragment(position: Int): Fragment =
            if (position == 1)
                LegalInfoFragment.newInstance()
            else
                OnboardingStepPageFragment.newInstance(position)
    }

    override fun onLocaleSettingsSelected() {
        onLocaleSelectedListener?.invoke()
    }

    override fun onTextsLoaded() {
        TODO("Not yet implemented")
    }

}
