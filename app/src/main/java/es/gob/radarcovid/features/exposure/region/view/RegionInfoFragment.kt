/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.region.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.HintSpinnerAdapter
import es.gob.radarcovid.databinding.FragmentRegionInfoBinding
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoPresenter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoView
import javax.inject.Inject

class RegionInfoFragment : BaseFragment(), RegionInfoView {

    companion object {

        fun newInstance(): RegionInfoFragment = RegionInfoFragment()

    }

    @Inject
    lateinit var presenter: RegionInfoPresenter

    private var _binding: FragmentRegionInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionInfoBinding.inflate(inflater, container, false)
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

    private fun initViews() {
        binding.wrapperPhone.setOnClickListener { presenter.onPhoneButtonClick() }
        binding.wrapperWeb.setOnClickListener { presenter.onWebButtonClick() }
        binding.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0)
                    presenter.onRegionSelected()
            }

        }
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

    override fun showRegionInfo(phone: String, webName: String) {
        binding.wrapperRegionInfo.visibility = View.VISIBLE

        if (phone.isNotEmpty()) {
            binding.wrapperPhone.visibility = View.VISIBLE
            binding.textViewPhone.text = phone
        } else {
            binding.wrapperPhone.visibility = View.GONE
        }

        if (webName.isNotEmpty()) {
            binding.wrapperWeb.visibility = View.VISIBLE
            binding.textViewWeb.text = webName
        } else {
            binding.wrapperWeb.visibility = View.VISIBLE
        }

    }

    override fun getSelectedRegionIndex(): Int =
        binding.spinnerRegion.selectedItemPosition - 1 // POSITION 0 IS THE "NON SELECTED" OPTION

}

