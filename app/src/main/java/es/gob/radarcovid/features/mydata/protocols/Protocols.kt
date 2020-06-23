package es.gob.radarcovid.features.mydata.protocols

interface MyDataView {

}

interface MyDataPresenter {

    fun viewReady()

    fun onClearDataButtonClick()

    fun onConditionsButtonClick()

    fun onPrivacyButtonClick()

}

interface MyDataRouter {

    fun navigateToConditions()

    fun navigateToPrivacyPolicy()

}