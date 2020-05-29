package com.indra.contacttracing.features.splash.presenter

import com.indra.contacttracing.features.profile.protocols.ProfileView
import com.indra.contacttracing.features.splash.protocols.SplashPresenter
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(private val view: ProfileView) : SplashPresenter {

    override fun viewReady() {
        TODO("Not yet implemented")
    }

}