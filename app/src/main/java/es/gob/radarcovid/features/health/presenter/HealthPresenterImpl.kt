package es.gob.radarcovid.features.health.presenter

import es.gob.radarcovid.features.health.protocols.HealthPresenter
import es.gob.radarcovid.features.health.protocols.HealthView
import javax.inject.Inject

class HealthPresenterImpl @Inject constructor(private val view:HealthView):HealthPresenter {

    override fun viewReady() {

    }

}