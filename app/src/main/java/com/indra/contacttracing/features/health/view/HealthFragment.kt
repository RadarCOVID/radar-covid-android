package com.indra.contacttracing.features.health.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseFragment
import com.indra.contacttracing.features.health.protocols.HealthPresenter
import com.indra.contacttracing.features.health.protocols.HealthView
import javax.inject.Inject

class HealthFragment : BaseFragment(), HealthView {

    companion object {

        fun newInstance() = HealthFragment()

    }

    @Inject
    lateinit var presenter: HealthPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewReady()
    }
}