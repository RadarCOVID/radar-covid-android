package com.indra.coronaradar.features.mydata.presenter

import com.indra.coronaradar.datamanager.usecase.ClearExposureDataUseCase
import com.indra.coronaradar.features.mydata.protocols.MyDataPresenter
import com.indra.coronaradar.features.mydata.protocols.MyDataView
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