package com.indra.contacttracing.features.main.protocols

import com.indra.contacttracing.common.view.RequestView

interface MainView : RequestView {

}

interface MainPresenter {

    fun viewReady()

}