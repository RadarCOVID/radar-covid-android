package es.gob.radarcovid.features.onboarding.pages.legal.view

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
        checkBoxTermsAndConditions.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            presenter.onLegalTermsCheckedChange(
                isChecked
            )
        })

        val spannableString = SpannableString(getString(R.string.legal_info_check))
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

        val clickableSpanConditions: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                presenter.onConditions()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.black_28)
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
        spannableString.setSpan(clickableSpanPrivacy, 10, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpanConditions, 47, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
