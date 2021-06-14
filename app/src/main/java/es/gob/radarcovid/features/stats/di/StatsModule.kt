/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.stats.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.stats.presenter.StatsPresenterImpl
import es.gob.radarcovid.features.stats.protocols.StatsPresenter
import es.gob.radarcovid.features.stats.protocols.StatsView
import es.gob.radarcovid.features.stats.view.StatsFragment

@Module
class StatsModule {

    @Provides
    fun providesFragment(fragment: StatsFragment): Fragment = fragment

    @Provides
    fun providesView(fragment: StatsFragment): StatsView = fragment

    @Provides
    @PerActivity
    fun providesPresenter(presenter: StatsPresenterImpl): StatsPresenter = presenter
}