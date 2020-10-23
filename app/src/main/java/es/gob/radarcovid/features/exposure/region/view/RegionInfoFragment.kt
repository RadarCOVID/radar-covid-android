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
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.ListDialog
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoPresenter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoView
import kotlinx.android.synthetic.main.fragment_region_info.*
import javax.inject.Inject

class RegionInfoFragment : BaseFragment(), RegionInfoView {

    companion object {

        fun newInstance(): RegionInfoFragment = RegionInfoFragment()

    }

    @Inject
    lateinit var presenter: RegionInfoPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_region_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        wrapperPhone.setOnClickListener { presenter.onPhoneButtonClick() }
        wrapperWeb.setOnClickListener { presenter.onWebButtonClick() }
        buttonSelectRegion.setOnClickListener { presenter.onRegionButtonClick() }
    }

    override fun showRegionSelector(
        regions: List<String>,
        selectedRegionIndex: Int
    ) {
        ListDialog.Builder(context!!)
            .setTitle(
                labelManager.getText(
                    "LOCALE_SELECTION_REGION_DEFAULT",
                    R.string.locale_selection_region_default
                ).toString()
            ).setItems(regions, selectedRegionIndex) { dialog, position ->
                presenter.onRegionSelected(position)
                dialog.dismiss()
            }
            .build()
            .show()
    }

    override fun showRegionInfo(name: String, phone: String, webName: String) {

        buttonSelectRegion.text = name
        buttonSelectRegion.contentDescription =
            "${labelManager.getText("ACC_SELECTED", R.string.single_choice_selected)} $name"

        wrapperRegionInfo.visibility = View.VISIBLE

        if (phone.isNotEmpty()) {
            wrapperPhone.visibility = View.VISIBLE
            textViewPhone.text = phone
        } else {
            wrapperPhone.visibility = View.GONE
        }

        if (webName.isNotEmpty()) {
            wrapperWeb.visibility = View.VISIBLE
            textViewWeb.text = webName
        } else {
            wrapperWeb.visibility = View.GONE
        }

    }
    
}