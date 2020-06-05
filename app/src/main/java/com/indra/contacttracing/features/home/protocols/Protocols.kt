package com.indra.contacttracing.features.home.protocols

interface HomeView {

    fun showLowExpositionLevel()

    fun showMediumExpositionLevel()

    fun showHighExpositionLevel()

    fun setLastUpdateTime(lastUpdateTime: String)

    fun setBluetoothBlockEnabled(enabled: Boolean)

}

interface HomePresenter {

    fun viewReady()

    fun onExpositionBlockClick()

    fun onReportButtonClick()

}

interface HomeRouter {

    fun navigateToExpositionDetail()

    fun navigateToCovidReport()

}