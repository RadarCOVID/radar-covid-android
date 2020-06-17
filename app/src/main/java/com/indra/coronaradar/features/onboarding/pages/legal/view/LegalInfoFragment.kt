package com.indra.coronaradar.features.onboarding.pages.legal.view

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseFragment
import com.indra.coronaradar.features.onboarding.pages.OnboardingStepPageFragment
import com.indra.coronaradar.features.onboarding.pages.legal.protocols.LegalInfoPresenter
import com.indra.coronaradar.features.onboarding.pages.legal.protocols.LegalInfoView
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
        if (requestCode == TermsAndConditionsActivity.REQUEST_CODE_LEGAL_TERMS && resultCode == Activity.RESULT_OK)
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
        checkBoxTermsAndConditions.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            presenter.onLegalTermsCheckedChange(
                isChecked
            )
        })

        val spannableString = SpannableString(getString(R.string.legal_info_check))
        val clickableSpanConditions: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                presenter.onLegalTermsButtonClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.black_28)
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }

        val clickableSpanPrivacy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                presenter.onPrivacyPolicyButtonClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.black_28)
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
        spannableString.setSpan(clickableSpanConditions, 11, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpanPrivacy, 35, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        checkBoxTermsAndConditions.setText(spannableString)

        buttonAccept.setOnClickListener { (activity as? OnboardingStepPageFragment.Callback)?.onContinueButtonClick() }

    }

    override fun showCheckWarning() {
        wrapperCheckWarning.visibility = View.VISIBLE
    }

    override fun hideCheckWarning() {
        wrapperCheckWarning.visibility = View.INVISIBLE
    }

    override fun setLegalTermsChecked() {
        checkBoxTermsAndConditions.isChecked = true
    }

    override fun setContinueButtonEnabled(enabled: Boolean) {
        buttonAccept.isEnabled = enabled
    }

}
