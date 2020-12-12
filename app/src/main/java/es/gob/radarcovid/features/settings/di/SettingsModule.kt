/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.settings.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import es.gob.radarcovid.features.settings.presenter.SettingsPresenterImpl
import es.gob.radarcovid.features.settings.protocols.SettingsPresenter
import es.gob.radarcovid.features.settings.protocols.SettingsView
import es.gob.radarcovid.features.settings.view.SettingsFragment

@Module
class SettingsModule {

    @Provides
    fun providesContext(fragment: SettingsFragment): Context = fragment.context!!

    @Provides
    fun providesFragment(fragment: SettingsFragment): Fragment = fragment

    @Provides
    fun providesView(fragment: SettingsFragment): SettingsView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: SettingsPresenterImpl): SettingsPresenter = presenter
}