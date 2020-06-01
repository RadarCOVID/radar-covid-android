package com.indra.contacttracing.features.profile.presenter

import com.indra.contacttracing.features.profile.protocols.ProfilePresenter
import com.indra.contacttracing.features.profile.protocols.ProfileView
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(private val view: ProfileView) : ProfilePresenter {

    override fun viewReady() {

    }

}