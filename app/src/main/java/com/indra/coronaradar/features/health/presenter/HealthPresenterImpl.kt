package com.indra.coronaradar.features.health.presenter

import com.indra.coronaradar.features.health.protocols.HealthPresenter
import com.indra.coronaradar.features.health.protocols.HealthView
import javax.inject.Inject

class HealthPresenterImpl @Inject constructor(private val view:HealthView):HealthPresenter {

    override fun viewReady() {

    }

}