package com.indra.contacttracing.features.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseFragment
import com.indra.contacttracing.features.profile.protocols.ProfilePresenter
import com.indra.contacttracing.features.profile.protocols.ProfileView
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var presenter:ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewReady()
    }
}