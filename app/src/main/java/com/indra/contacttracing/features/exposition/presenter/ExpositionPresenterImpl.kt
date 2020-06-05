package com.indra.contacttracing.features.exposition.presenter

import com.indra.contacttracing.features.exposition.protocols.ExpositionPresenter
import com.indra.contacttracing.features.exposition.protocols.ExpositionView
import javax.inject.Inject

class ExpositionPresenterImpl @Inject constructor(private val view: ExpositionView) :
    ExpositionPresenter {

    override fun viewReady() {

    }

}