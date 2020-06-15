package com.indra.coronaradar.features.helpline.presenter

import com.indra.coronaradar.features.helpline.protocols.HelplinePresenter
import com.indra.coronaradar.features.helpline.protocols.HelplineRouter
import com.indra.coronaradar.features.helpline.protocols.HelplineView
import javax.inject.Inject

class HelplinePresenterImpl @Inject constructor(
    private val view: HelplineView,
    private val router: HelplineRouter
) : HelplinePresenter {

    override fun viewReady() {

    }

    override fun onStartButtonClick() {
        //router.navigateToBrowser("http://www.google.com")
        router.navigateToPoll()
    }

}