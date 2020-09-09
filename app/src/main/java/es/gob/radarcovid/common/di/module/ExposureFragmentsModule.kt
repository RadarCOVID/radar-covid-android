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
import es.gob.radarcovid.features.exposure.region.di.RegionInfoModule
import es.gob.radarcovid.features.exposure.region.view.RegionInfoFragment

@Module
abstract class ExposureFragmentsModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [RegionInfoModule::class])
    abstract fun bindsRegionInfoFragment(): RegionInfoFragment

}