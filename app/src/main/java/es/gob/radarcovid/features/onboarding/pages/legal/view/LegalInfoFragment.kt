package es.gob.radarcovid.features.onboarding.pages.legal.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.onboarding.pages.OnboardingStepPageFragment
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import es.gob.radarcovid.features.onboarding.pages.legal.protocols.LegalInfoView
import kotlinx.android.synthetic.main.fragment_legal_info.*
import javax.inject.Inject


class LegalInfoFragment : BaseFragment(), LegalInfoView {

    companion object {

        fun newInstance() = LegalInfoFragment()

    }

    @Inject
    lateinit var presenter: LegalInfoPresenter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ConditionsActivity.REQUEST_CODE_LEGAL_TERMS && resultCode == Activity.RESULT_OK)
            presenter.onLegalTermsAccepted()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_legal_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        checkBoxTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            presenter.onLegalTermsCheckedChange(
                isChecked
            )
        }

        textViewPrivacyPolicy.setOnClickListener { presenter.onPrivacyPolicyButtonClick() }
        textViewUsageConditions.setOnClickListener { presenter.onConditionsButtonClick() }

        buttonAccept.setOnClickListener { (activity as? OnboardingStepPageFragment.Callback)?.onContinueButtonClick() }

    }

    override fun setLegalTermsChecked() {
        checkBoxTermsAndConditions.isChecked = true
    }

    override fun setContinueButtonEnabled(enabled: Boolean) {
        buttonAccept.isEnabled = enabled
    }

}
