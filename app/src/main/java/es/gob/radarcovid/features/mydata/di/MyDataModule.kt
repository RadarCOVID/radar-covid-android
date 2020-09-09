/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.mydata.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.mydata.presenter.MyDataPresenterImpl
import es.gob.radarcovid.features.mydata.protocols.MyDataPresenter
import es.gob.radarcovid.features.mydata.protocols.MyDataRouter
import es.gob.radarcovid.features.mydata.protocols.MyDataView
import es.gob.radarcovid.features.mydata.router.MyDataRouterImpl
import es.gob.radarcovid.features.mydata.view.MyDataFragment

@Module
class MyDataModule {

    @Provides
    fun providesFragment(fragment: MyDataFragment): Fragment = fragment

    @Provides
    fun providesView(fragment: MyDataFragment): MyDataView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: MyDataPresenterImpl): MyDataPresenter = presenter

    @Provides
    @PerFragment
    fun providesRouter(router: MyDataRouterImpl): MyDataRouter = router

}