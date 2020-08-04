package es.gob.radarcovid.features.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.gob.radarcovid.R
import es.gob.radarcovid.features.onboarding.view.OnboardingPageCallback
import kotlinx.android.synthetic.main.fragment_activation_page.*

class ActivationPageFragment : Fragment() {

    companion object {

        fun newInstance() = ActivationPageFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activation_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonContinue.setOnClickListener {
            (activity as? OnboardingPageCallback)?.onFinishButtonClick(true)
        }
    }

}