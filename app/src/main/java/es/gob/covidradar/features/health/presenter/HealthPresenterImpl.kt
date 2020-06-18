package es.gob.covidradar.features.health.presenter

import es.gob.covidradar.features.health.protocols.HealthPresenter
import es.gob.covidradar.features.health.protocols.HealthView
import javax.inject.Inject

class HealthPresenterImpl @Inject constructor(private val view:HealthView):HealthPresenter {

    override fun viewReady() {

    }

}