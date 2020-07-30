package es.gob.radarcovid.features.locale.protocols

import es.gob.radarcovid.common.view.RequestView

interface LocaleSelectionView : RequestView {

    fun setRegions(regions: List<String>)

    fun setSelectedRegionIndex(index: Int)

    fun getSelectedRegionIndex(): Int

    fun setLanguages(languages: List<String>)

    fun setSelectedLanguageIndex(index: Int)

    fun getSelectedLanguageIndex(): Int

    fun reloadLabels()

}

interface LocaleSelectionPresenter {

    fun viewReady()

    fun onApplyButtonClick()

    fun onLanguageSelectionChange(index: Int)

    fun isLanguageChanged(): Boolean

    fun applyLocaleSettings()

    fun restoreLocaleSettings()

}

interface LocaleSelectionRouter {

    fun restartApplication()

}