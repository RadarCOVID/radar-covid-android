package es.gob.radarcovid.features.locale.protocols

import es.gob.radarcovid.common.view.RequestView

interface LocaleSelectionView : RequestView {

    fun setRegions(regions: List<String>)

    fun getSelectedRegionIndex(): Int

    fun reloadLabels()

}

interface LocaleSelectionPresenter {

    fun viewReady()

    fun onApplyButtonClick()

}