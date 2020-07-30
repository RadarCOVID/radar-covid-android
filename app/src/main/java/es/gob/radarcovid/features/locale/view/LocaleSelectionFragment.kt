package es.gob.radarcovid.features.locale.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.HintSpinnerAdapter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import kotlinx.android.synthetic.main.fragment_locale_selection.*
import javax.inject.Inject

class LocaleSelectionFragment : BaseFragment(), LocaleSelectionView {

    companion object {
        fun newInstance() = LocaleSelectionFragment
    }

    @Inject
    lateinit var presenter: LocaleSelectionPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locale_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    override fun setRegions(regions: List<String>) {
        spinnerRegion.adapter =
            HintSpinnerAdapter(
                context!!,
                labelManager.getText(
                    "LOCALE_SELECTION_REGION_DEFAULT",
                    R.string.locale_selection_region_default
                ).toString(),
                R.layout.row_spinner,
                regions
            )
    }

    override fun setSelectedRegionIndex(index: Int) {
        spinnerRegion.setSelection(index + 1) // POSITION 0 IS THE "NON SELECTED" OPTION
    }

    override fun getSelectedRegionIndex(): Int =
        spinnerRegion.selectedItemPosition - 1 // POSITION 0 IS THE "NON SELECTED" OPTION

    override fun setLanguages(languages: List<String>) {
        spinnerLanguage.adapter =
            HintSpinnerAdapter(
                context!!,
                labelManager.getText(
                    "",
                    R.string.locale_selection_language_default
                ).toString(),
                R.layout.row_spinner,
                languages
            )
    }

    override fun setSelectedLanguageIndex(index: Int) {
        spinnerLanguage.setSelection(index + 1) // POSITION 0 IS THE "NON SELECTED" OPTION
    }

    override fun getSelectedLanguageIndex(): Int =
        spinnerLanguage.selectedItemPosition - 1 // POSITION 0 IS THE "NON SELECTED" OPTION

    override fun reloadLabels() {
        labelManager.reload()
    }

    private fun initViews() {
        spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

        }

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0)
                    presenter.onLanguageSelectionChange(position - 1) // POSITION 0 IS THE "NON SELECTED" OPTION
            }

        }
    }

    fun isLanguageChanged(): Boolean = presenter.isLanguageChanged()

    fun applyLocaleSettings() = presenter.applyLocaleSettings()

    fun restoreLocaleSettings() = presenter.restoreLocaleSettings()

}