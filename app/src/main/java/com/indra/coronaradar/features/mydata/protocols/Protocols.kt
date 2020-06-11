package com.indra.coronaradar.features.mydata.protocols

interface MyDataView {

}

interface MyDataPresenter {

    fun viewReady()

    fun onClearDataButtonClick()

}