package com.indra.contacttracing.features.onboarding.view

import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.features.onboarding.protocols.OnboardingPresenter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingView
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

class OnboardingActivity : BaseActivity(), OnboardingView {

    @Inject
    lateinit var presenter: OnboardingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        button.setOnClickListener { presenter.onNavigateToMainButtonClick() }
    }
}
