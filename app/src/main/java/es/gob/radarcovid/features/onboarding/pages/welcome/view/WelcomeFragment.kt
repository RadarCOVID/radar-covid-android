package es.gob.radarcovid.features.onboarding.pages.welcome.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomePresenter
import es.gob.radarcovid.features.onboarding.pages.welcome.protocols.WelcomeView
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

class WelcomeFragment : BaseFragment(), WelcomeView {

    companion object {

        fun newInstance() = WelcomeFragment()

    }

    @Inject
    lateinit var presenter: WelcomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        buttonContinue.setOnClickListener { presenter.onContinueButtonClick() }
    }

    override fun isLocaleChanged(): Boolean =
        (childFragmentManager
            .findFragmentById(R.id.fragmentLocaleSelection) as LocaleSelectionFragment)
            .isLanguageChanged()

    override fun applyLocaleSettings() {
        (childFragmentManager
            .findFragmentById(R.id.fragmentLocaleSelection) as LocaleSelectionFragment)
            .applyLocaleSettings()
    }

    override fun restorePreviousLanguage() {
        (childFragmentManager
            .findFragmentById(R.id.fragmentLocaleSelection) as LocaleSelectionFragment)
            .restoreLocaleSettings()
    }

    override fun performContinueButtonClick() {
        (activity as? OnboardingPageCallback)?.onContinueButtonClick()
    }

    override fun showLanguageChangeDialog() {
        CMDialog.Builder(context!!)
            .setMessage(R.string.locale_selection_warning_message)
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_ACCEPT_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                it.dismiss()
                presenter.onLocaleChangeConfirm()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.question_dialog_cancel
                ).toString()
            ) {
                it.dismiss()
                presenter.onLocaleChangeCancel()
            }
            .setCloseButton { it.dismiss() }
            .build()
            .show()
    }

    override fun finish() {
        activity?.finish()
    }

}