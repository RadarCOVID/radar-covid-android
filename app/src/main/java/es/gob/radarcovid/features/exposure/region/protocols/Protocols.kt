package es.gob.radarcovid.features.exposure.region.protocols

interface RegionInfoView {

    fun setRegions(regions: List<String>)

    fun showRegionInfo(phone: String, web: String)

    fun getSelectedRegionIndex():Int

}

interface RegionInfoPresenter {

    fun viewReady()

    fun onPhoneButtonClick()

    fun onWebButtonClick()

    fun onRegionSelected()

}

interface RegionInfoRouter {

    fun navigateToDialer(phone: String)

    fun navigateToBrowser(url: String)

}