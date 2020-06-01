package com.indra.contacttracing.features.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indra.contacttracing.R
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
            1 -> wrapperStep1.visibility = View.VISIBLE
            2 -> wrapperStep2.visibility = View.VISIBLE
            3 -> {
                wrapperStep3.visibility = View.VISIBLE
                button.setText(R.string.onboarding_button_step_3)
            }
            4 -> wrapperStep4.visibility = View.VISIBLE
        }
    }

    interface Callback {

        fun onContinueButtonClick()

    }

}