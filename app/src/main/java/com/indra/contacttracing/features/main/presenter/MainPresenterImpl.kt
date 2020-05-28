package com.indra.contacttracing.features.main.presenter

import android.util.Log
import com.indra.contacttracing.datamanager.usecase.GetExampleUseCase
import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val exampleUseCase: GetExampleUseCase
) : MainPresenter {

    override fun viewReady() {
        exampleUseCase.getExample(onSuccess = {
            Log.d("Test", it)
        }, onError = {

        })
    }

}