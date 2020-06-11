package com.indra.coronaradar.features.main.presenter

import com.indra.coronaradar.datamanager.usecase.GetExampleUseCase
import com.indra.coronaradar.features.main.protocols.MainPresenter
import com.indra.coronaradar.features.main.protocols.MainRouter
import com.indra.coronaradar.features.main.protocols.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter,
    private val exampleUseCase: GetExampleUseCase
) : MainPresenter {

    override fun viewReady() {
//        view.showLoading()
//        exampleUseCase.getExample(onSuccess = {
//            view.hideLoading()
//            Log.d("Test", it)
//        }, onError = {
//            view.hideLoading()
//            view.showError(it)
//        })
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

    override fun onBackPressed() {
        view.showExitConfirmationDialog()
    }

    override fun onExitConfirmed() {
        view.finish()
    }

}