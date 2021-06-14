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
import es.gob.radarcovid.features.main.view.MainActivity
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
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
        return if (activity is MainActivity) {
            inflater.inflate(R.layout.fragment_locale_selection_settings, container, false)
        } else {
            inflater.inflate(R.layout.fragment_locale_selection, container, false)
        }

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
            ).setTitle(
                labelManager.getText(
                    "LOCALE_CHANGE_LANGUAGE",
                    R.string.locale_selection_region_title
                ).toString()
            )
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.cancel
                ).toString()
            ) {
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
            presenter.onLanguageDropdownButtonClick()
        }
    }

    override fun setLanguage(language: String) {
        if (activity is OnboardingActivity) {
            buttonLanguage.text = language
        }
        buttonLanguage.contentDescription =
            "${labelManager.getText(
                "ACC_BUTTON_SELECTOR_SELECT",
                R.string.settings_change_language_desc
            )} $language"
    }

    override fun showLanguageSelectionDialog(languages: List<String>, selectedIndex: Int) {
        ListDialog.Builder(context!!)
            .setTitle(
                labelManager.getText(
                    "SETTINGS_LANGUAGE_TITLE",
                    R.string.settings_language_title
                ).toString()
            )
            .setPositiveButton(
                labelManager.getText(
                    "ALERT_ACCEPT_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                presenter.onLanguagesListAcceptButtonClick {
                    it.dismiss()
                }
            }
            .setNegativeButton(
                labelManager.getText(
                    "ALERT_CANCEL_BUTTON",
                    R.string.accept
                ).toString()
            ) {
                it.dismiss()
            }.setItems(languages, selectedIndex) { _, position ->
                presenter.onLanguageSelectionChange(position)
            }
            .build()
            .show()
    }

}