package com.indra.contacttracing.features.home.presenter

import com.indra.contacttracing.features.home.protocols.HomePresenter
import com.indra.contacttracing.features.home.protocols.HomeView
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(private val view: HomeView):
    HomePresenter {

    override fun viewReady() {
        TODO("Not yet implemented")
    }

}