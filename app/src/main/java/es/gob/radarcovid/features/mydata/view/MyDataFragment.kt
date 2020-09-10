/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.mydata.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.databinding.FragmentMyDataBinding
import es.gob.radarcovid.features.mydata.protocols.MyDataPresenter
import es.gob.radarcovid.features.mydata.protocols.MyDataView
import javax.inject.Inject

class MyDataFragment : BaseFragment(), MyDataView {

    companion object {
        fun newInstance() = MyDataFragment()
    }

    @Inject
    lateinit var presenter: MyDataPresenter

    private lateinit var binding: FragmentMyDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        binding.textViewConditions.setOnClickListener { presenter.onConditionsButtonClick() }
        binding.textViewPrivacy.setOnClickListener { presenter.onPrivacyButtonClick() }
    }

}