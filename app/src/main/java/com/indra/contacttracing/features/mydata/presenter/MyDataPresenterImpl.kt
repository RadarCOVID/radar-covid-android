package com.indra.contacttracing.features.mydata.presenter

import com.indra.contacttracing.features.mydata.protocols.MyDataPresenter
import com.indra.contacttracing.features.mydata.protocols.MyDataView
import javax.inject.Inject

class MyDataPresenterImpl @Inject constructor(private val view: MyDataView) : MyDataPresenter {

    override fun viewReady() {

    }

}