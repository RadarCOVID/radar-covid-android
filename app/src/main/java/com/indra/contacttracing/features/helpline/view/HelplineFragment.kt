package com.indra.contacttracing.features.helpline.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseFragment
import com.indra.contacttracing.features.helpline.protocols.HelplinePresenter
import com.indra.contacttracing.features.helpline.protocols.HelplineView
import kotlinx.android.synthetic.main.fragment_helpline.*
import javax.inject.Inject

class HelplineFragment : BaseFragment(), HelplineView {

    companion object {

        fun newInstance() = HelplineFragment()

    }

    @Inject
    lateinit var presenter: HelplinePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_helpline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {

        buttonStart.setOnClickListener { presenter.onStartButtonClick() }

    }

}