/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioGroup
import es.gob.radarcovid.R


class SegmentedControl : RadioGroup {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Set background
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return adjustLayoutParams(super.generateLayoutParams(attrs))
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return adjustLayoutParams(super.generateDefaultLayoutParams() as LayoutParams)
    }

    private fun adjustLayoutParams(params: LayoutParams): LayoutParams {
        // Make all buttons the same size if the orientation is horizontal
        if (orientation == HORIZONTAL) {
            params.width = 0
            params.weight = 1f
        }
        return params
    }
}