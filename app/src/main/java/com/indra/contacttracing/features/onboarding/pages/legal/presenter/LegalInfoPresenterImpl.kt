package com.indra.contacttracing.features.onboarding.pages.legal.presenter

import com.indra.contacttracing.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import com.indra.contacttracing.features.onboarding.pages.legal.protocols.LegalInfoRouter
import com.indra.contacttracing.features.onboarding.pages.legal.protocols.LegalInfoView
import javax.inject.Inject

class LegalInfoPresenterImpl @Inject constructor(
    private val view: LegalInfoView,
    private val router: LegalInfoRouter
) : LegalInfoPresenter {

    override fun viewReady() {

    }

    override fun onLegalTermsCheckedChange(checked: Boolean) {
        if (checked)
            view.hideCheckWarning()
        else
            view.showCheckWarning()

        view.setContinueButtonEnabled(checked)

    }

}