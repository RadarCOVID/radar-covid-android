/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.locale.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.HintSpinnerAdapter
import es.gob.radarcovid.databinding.FragmentLocaleSelectionBinding
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import javax.inject.Inject

class LocaleSelectionFragment : BaseFragment(), LocaleSelectionView {

    companion object {
        fun newInstance() = LocaleSelectionFragment
    }

    @Inject
    lateinit var presenter: LocaleSelectionPresenter

    private var _binding: FragmentLocaleSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocaleSelectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setRegions(regions: List<String>) {
        binding.spinnerRegion.adapter =
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
        binding.spinnerRegion.setSelection(index + 1) // POSITION 0 IS THE "NON SELECTED" OPTION
    }

    override fun getSelectedRegionIndex(): Int =
        binding.spinnerRegion.selectedItemPosition - 1 // POSITION 0 IS THE "NON SELECTED" OPTION

    override fun setLanguages(languages: List<String>) {
        binding.spinnerLanguage.adapter =
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
        binding.spinnerLanguage.setSelection(index + 1) // POSITION 0 IS THE "NON SELECTED" OPTION
    }

    override fun getSelectedLanguageIndex(): Int =
        binding.spinnerLanguage.selectedItemPosition - 1 // POSITION 0 IS THE "NON SELECTED" OPTION

    override fun reloadLabels() {
        labelManager.reload()
    }

    private fun initViews() {
        binding.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

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

        binding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

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