package com.indra.contacttracing.features.main.presenter

import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(private val view: MainView) : MainPresenter {

    override fun viewReady() {

    }

}