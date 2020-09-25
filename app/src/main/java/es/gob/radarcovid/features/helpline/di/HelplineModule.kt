/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.helpline.di

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import es.gob.radarcovid.features.helpline.presenter.HelplinePresenterImpl
import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import es.gob.radarcovid.features.helpline.router.HelplineRouterImpl
import es.gob.radarcovid.features.helpline.view.HelplineFragment

@Module
class HelplineModule {

    @Provides
    fun providesContext(fragment: HelplineFragment): Context = fragment.context!!

    @Provides
    fun providesActivity(fragment: HelplineFragment): Activity = fragment.activity!!

    @Provides
    fun providesView(fragment: HelplineFragment): HelplineView = fragment

    @Provides
    fun providesPresenter(presenter: HelplinePresenterImpl): HelplinePresenter = presenter

    @Provides
    fun providesRouter(router: HelplineRouterImpl): HelplineRouter = router

}