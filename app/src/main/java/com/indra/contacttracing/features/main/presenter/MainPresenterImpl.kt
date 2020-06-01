package com.indra.contacttracing.features.main.presenter

import android.util.Log
import com.indra.contacttracing.datamanager.usecase.GetExampleUseCase
import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainRouter
import com.indra.contacttracing.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val exampleUseCase: GetExampleUseCase
) : MainPresenter {

    override fun viewReady() {
        view.showLoading()
        exampleUseCase.getExample(onSuccess = {
            view.hideLoading()
            Log.d("Test", it)
        }, onError = {
            view.hideLoading()
            view.showError(it)
        })

        router.navigateToHome()
    }

    override fun onHomeButtonClick() {
        router.navigateToHome()
    }

    override fun onHealthButtonClick() {
        router.navigateToHealth()
    }

    override fun onProfileButtonClick() {
        router.navigateToProfile()
    }

    override fun onHelplineButtonClick() {
        router.navigateToHelpline()
    }

}