package com.indra.contacttracing.features.onboarding.view

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.features.onboarding.pages.OnboardingStepPageFragment
import com.indra.contacttracing.features.onboarding.pages.legal.view.LegalInfoFragment
import com.indra.contacttracing.features.onboarding.protocols.OnboardingPresenter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingView
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

class OnboardingActivity : BaseActivity(), OnboardingView, OnboardingStepPageFragment.Callback {

    companion object {

        private const val REQUEST_CODE_BLUETOOTH: Int = 1

    }

    @Inject
    lateinit var presenter: OnboardingPresenter

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
        if (viewPager.currentItem == 0)
            super.onBackPressed()
        else
            presenter.onBackButtonPressed()
    }

    override fun onContinueButtonClick() {
        presenter.onContinueButtonClick(viewPager.currentItem, viewPager.adapter?.itemCount ?: 0)
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

    private class OnboardingAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val totalPages = 5

        override fun getItemCount(): Int = totalPages

        override fun createFragment(position: Int): Fragment =
            if (position == 0)
                LegalInfoFragment.newInstance()
            else
                OnboardingStepPageFragment.newInstance(position)
    }

}
