/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.locale.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.locale.presenter.LocaleSelectionPresenterImpl
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionRouter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import es.gob.radarcovid.features.locale.router.LocaleSelectionRouterImpl
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment

@Module
class LocaleSelectionModule {

    @Provides
    fun providesView(fragment: LocaleSelectionFragment): LocaleSelectionView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: LocaleSelectionPresenterImpl): LocaleSelectionPresenter =
        presenter

    @Provides
    @PerFragment
    fun providesRouter(router: LocaleSelectionRouterImpl): LocaleSelectionRouter = router

}