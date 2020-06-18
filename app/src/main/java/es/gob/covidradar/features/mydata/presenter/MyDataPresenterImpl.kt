package es.gob.covidradar.features.mydata.presenter

import es.gob.covidradar.datamanager.usecase.ClearExposureDataUseCase
import es.gob.covidradar.features.mydata.protocols.MyDataPresenter
import es.gob.covidradar.features.mydata.protocols.MyDataView
import javax.inject.Inject

class MyDataPresenterImpl @Inject constructor(
    private val view: MyDataView,
    private val clearExposureDataUseCase: ClearExposureDataUseCase
) : MyDataPresenter {

    override fun viewReady() {

    }

    override fun onClearDataButtonClick() {
        clearExposureDataUseCase.clearExposureData()
    }

}