/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.datamanager.mapper

import com.nhaarman.mockitokotlin2.mock
import es.gob.radarcovid.datamanager.utils.LabelManager
import org.junit.Assert.assertNotNull
import org.junit.Test

class RegionsDataMapperUnitTest {

    private val labelManager: LabelManager = mock()
    private val regionsDataMapper = RegionsDataMapper(labelManager)

    @Test
    fun testDefaultValues() {
        assertNotNull(regionsDataMapper.transform(null))
    }

}