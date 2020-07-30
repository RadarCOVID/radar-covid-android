package es.gob.radarcovid.features.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.gob.radarcovid.R
import es.gob.radarcovid.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_1
import es.gob.radarcovid.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_3
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import kotlinx.android.synthetic.main.fragment_onboarding_step.*
import kotlinx.android.synthetic.main.layout_onboarding_step1.*
import kotlinx.android.synthetic.main.layout_onboarding_step3.*

class OnboardingStepPageFragment : Fragment() {

    companion object {
        private const val ARG_STEP = "arg_step"

        fun newInstance(step: Int) = OnboardingStepPageFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(ARG_STEP, step)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonContinue.setOnClickListener {
            (activity as? OnboardingPageCallback)?.onContinueButtonClick()
        }
        buttonActivate.setOnClickListener {
            (activity as? OnboardingPageCallback)?.onFinishButtonClick(true)
        }
        buttonFinish.setOnClickListener {
            (activity as? OnboardingPageCallback)?.onFinishButtonClick(false)
        }
        (arguments?.getInt(ARG_STEP) ?: 0).let { pageIndex ->
            when (pageIndex) {
                ONBOARDING_PAGE_INDEX_STEP_1 -> wrapperStep1.visibility = View.VISIBLE
                ONBOARDING_PAGE_INDEX_STEP_3 -> wrapperStep3.visibility = View.VISIBLE
            }
        }
    }

}