package com.indra.contacttracing.features.helpline.presenter

import com.indra.contacttracing.features.helpline.protocols.HelplinePresenter
import com.indra.contacttracing.features.helpline.protocols.HelplineView
import javax.inject.Inject

class HelpLinePresenterImpl @Inject constructor(private val view:HelplineView):HelplinePresenter {

    override fun viewReady() {
        TODO("Not yet implemented")
    }

}