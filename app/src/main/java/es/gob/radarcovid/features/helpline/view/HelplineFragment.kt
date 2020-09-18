/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.helpline.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import kotlinx.android.synthetic.main.fragment_helpline.*
import javax.inject.Inject


class HelplineFragment : BaseFragment(), HelplineView {

    companion object {

        fun newInstance() = HelplineFragment()

    }

    @Inject
    lateinit var presenter: HelplinePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_helpline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        textViewFaqsTitle.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "HELPLINE_FAQS_WEB_URL",
                    R.string.helpline_faqs_web_url
                ).toString()
            )
        }
        textViewInfoWebTitle.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "HELPLINE_INFO_WEB_URL",
                    R.string.helpline_info_web_url
                ).toString()
            )
        }
        textViewOtherWebTitle.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "HELPLINE_OTHER_WEB_URL",
                    R.string.helpline_other_web_url
                ).toString()
            )
        }
    }

    override fun showDialerForSupport() {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse(
                "tel:${labelManager.getContactPhone()}"
            )
        })
    }

}