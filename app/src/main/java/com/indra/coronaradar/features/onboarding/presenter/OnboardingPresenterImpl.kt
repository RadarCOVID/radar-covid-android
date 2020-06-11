package com.indra.coronaradar.features.onboarding.presenter

import com.indra.coronaradar.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_3
import com.indra.coronaradar.features.onboarding.protocols.OnboardingPresenter
import com.indra.coronaradar.features.onboarding.protocols.OnboardingRouter
import com.indra.coronaradar.features.onboarding.protocols.OnboardingView
import javax.inject.Inject

class OnboardingPresenterImpl @Inject constructor(
    private val view: OnboardingView,
    private val router: OnboardingRouter
) : OnboardingPresenter {

    override fun viewReady() {

    }

    override fun onExitConfirmed() {
        view.finish()
    }

    override fun onBackButtonPressed(isFirstItem: Boolean) {
        if (isFirstItem)
            view.showExitConfirmationDialog()
        else
            view.showPreviousPage()
    }

    override fun onContinueButtonClick(page: Int, totalPages: Int) {
        when (page) {
            totalPages - 1 -> {
                router.navigateToMain()
                view.finish()
            }
            ONBOARDING_PAGE_INDEX_STEP_3 -> {
                if (view.isBluetoothEnabled())
                    view.showNextPage()
                else
                    view.showBluetoothRequest()
            }
            else -> {
                view.showNextPage()
            }
        }
    }

    override fun onBluetoothEnabled() {
        view.showNextPage()
    }

}