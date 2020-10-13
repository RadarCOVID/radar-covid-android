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
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseFragment
import es.gob.radarcovid.common.view.CMDialog
import es.gob.radarcovid.common.view.ListDialog
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

    override fun reloadLabels() {
        labelManager.reload()
    }

    override fun showLanguageChangeDialog() {
        CMDialog.Builder(context!!)
            .setMessage(
                labelManager.getText(
                    "LOCALE_CHANGE_WARNING",
                    R.string.locale_selection_warning_message
                ).toString()
            ).setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.cancel
                ).toString()
            ) {
                presenter.restoreLocaleSettings()
                it.dismiss()
            }
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_ACCEPT_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                it.dismiss()
                presenter.onLocaleChangeConfirm()
            }
            .build()
            .show()
    }

    private fun initViews() {

        buttonLanguage.setOnClickListener {
            presenter.onSelectLanguageClick()
        }

    }

    fun restoreLocaleSettings() = presenter.restoreLocaleSettings()

    override fun setLanguage(language: String) {
        buttonLanguage.text = language
    }

    override fun showLanguageSelectionDialog(languages: List<String>, index: Int) {
        ListDialog.Builder(context!!)
            .setTitle(
                labelManager.getText(
                    "SETTINGS_LANGUAGE_TITLE",
                    R.string.settings_language_title
                ).toString()
            )
            .setList(languages)
            .setSelected(index)
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_ACCEPT_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                it.dismiss()
                showLanguageChangeDialog()
            }
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                it.dismiss()
            }
            .setOnItemClick(object : ListDialog.OnItemClickListener {
                override fun onClickResult(
                    position: Int
                ) {
                    presenter.onLanguageSelectionChange(position)
                }
            })
            .show()
    }

}