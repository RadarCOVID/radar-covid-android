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
import es.gob.radarcovid.features.venuerecord.pages.checkin.di.CheckInModule
import es.gob.radarcovid.features.venuerecord.pages.checkin.view.CheckInFragment
import es.gob.radarcovid.features.venuerecord.pages.checkout.di.CheckOutModule
import es.gob.radarcovid.features.venuerecord.pages.checkout.view.CheckOutFragment
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.di.ErrorCapturedCodeModule
import es.gob.radarcovid.features.venuerecord.pages.errorcapturedcode.view.ErrorCapturedCodeFragment
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.di.RecordInitiatedModule
import es.gob.radarcovid.features.venuerecord.pages.recordinitiated.view.RecordInitiatedFragment
import es.gob.radarcovid.features.venuerecord.pages.recordsuccess.di.RecordSuccessModule
import es.gob.radarcovid.features.venuerecord.pages.recordsuccess.view.RecordSuccessFragment

@Module
abstract class VenueRecordFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [CheckInModule::class])
    abstract fun bindsCapturedCodeFragment(): CheckInFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ErrorCapturedCodeModule::class])
    abstract fun bindsErrorCapturedCodeFragment(): ErrorCapturedCodeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [RecordInitiatedModule::class])
    abstract fun bindsRecordInitiatedFragment(): RecordInitiatedFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [CheckOutModule::class])
    abstract fun bindsCheckOutFragment(): CheckOutFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [RecordSuccessModule::class])
    abstract fun bindsRecordSuccessFragment(): RecordSuccessFragment

}