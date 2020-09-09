/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.exposure.region.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.features.exposure.region.presenter.RegionInfoPresenterImpl
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoPresenter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoRouter
import es.gob.radarcovid.features.exposure.region.protocols.RegionInfoView
import es.gob.radarcovid.features.exposure.region.router.RegionInfoRouterImpl
import es.gob.radarcovid.features.exposure.region.view.RegionInfoFragment
import javax.inject.Inject

@Module
class RegionInfoModule {

    @Provides
    fun providesView(fragment: RegionInfoFragment): RegionInfoView = fragment

    @Inject
    @Provides
    fun providesPresenter(presenter: RegionInfoPresenterImpl): RegionInfoPresenter = presenter

    @Inject
    @Provides
    fun providesRouter(router: RegionInfoRouterImpl): RegionInfoRouter = router

}