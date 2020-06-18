package es.gob.covidradar.features.mydata.protocols

interface MyDataView {

}

interface MyDataPresenter {

    fun viewReady()

    fun onClearDataButtonClick()

}