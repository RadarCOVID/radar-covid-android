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
import es.gob.radarcovid.databinding.FragmentHelplineBinding
import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import javax.inject.Inject

class HelplineFragment : BaseFragment(), HelplineView {

    companion object {

        fun newInstance() = HelplineFragment()

    }

    @Inject
    lateinit var presenter: HelplinePresenter

    private lateinit var binding: FragmentHelplineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelplineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        binding.textViewFaqsTitle.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "HELPLINE_FAQS_WEB_URL",
                    R.string.helpline_faqs_web_url
                ).toString()
            )
        }
        binding.textViewInfoWebTitle.setOnClickListener {
            presenter.onUrlButtonClick(
                labelManager.getText(
                    "HELPLINE_INFO_WEB_URL",
                    R.string.helpline_info_web_url
                ).toString()
            )
        }
        binding.textViewOtherWebTitle.setOnClickListener {
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

    override fun sendMailToInterview() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(labelManager.getText("CONTACT_EMAIL", R.string.contact_email).toString())
            )
//            putExtra(Intent.EXTRA_SUBJECT, "Subject")
//            putExtra(Intent.EXTRA_TEXT, "Text")
        }

        startActivity(
            Intent.createChooser(
                emailIntent,
                "Send mail..."
            )
        )
    }

}