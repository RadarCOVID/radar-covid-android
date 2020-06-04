package com.indra.contacttracing.features.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indra.contacttracing.R
import com.indra.contacttracing.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_1
import com.indra.contacttracing.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_2
import com.indra.contacttracing.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_3
import com.indra.contacttracing.features.onboarding.protocols.ONBOARDING_PAGE_INDEX_STEP_4
import kotlinx.android.synthetic.main.fragment_onboarding_step.*

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
        button.setOnClickListener {
            (activity as? Callback)?.onContinueButtonClick()
        }
        when (arguments?.getInt(ARG_STEP) ?: 0) {
            ONBOARDING_PAGE_INDEX_STEP_1 -> wrapperStep1.visibility = View.VISIBLE
            ONBOARDING_PAGE_INDEX_STEP_2 -> wrapperStep2.visibility = View.VISIBLE
            ONBOARDING_PAGE_INDEX_STEP_3 -> {
                wrapperStep3.visibility = View.VISIBLE
                button.setText(R.string.onboarding_button_step_3)
            }
            ONBOARDING_PAGE_INDEX_STEP_4 -> wrapperStep4.visibility = View.VISIBLE
        }
    }

    interface Callback {

        fun onContinueButtonClick()

    }

}