package es.gob.covidradar.features.helpline.presenter

import es.gob.covidradar.features.helpline.protocols.HelplinePresenter
import es.gob.covidradar.features.helpline.protocols.HelplineRouter
import es.gob.covidradar.features.helpline.protocols.HelplineView
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