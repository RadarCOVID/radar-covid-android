/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.venuerecord.pages.capturedcode.di.CapturedCodeModule
import es.gob.radarcovid.features.venuerecord.pages.capturedcode.view.CapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.di.ErrorCapturedCodeModule
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.view.ErrorCapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.di.RecordInitiatedModule
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.view.RecordInitiatedFragment

@Module
abstract class VenueRecordFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [CapturedCodeModule::class])
    abstract fun bindsCapturedCodeFragment(): CapturedCodeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ErrorCapturedCodeModule::class])
    abstract fun bindsErrorCapturedCodeFragment(): ErrorCapturedCodeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [RecordInitiatedModule::class])
    abstract fun bindsRecordInitiatedFragment(): RecordInitiatedFragment
}