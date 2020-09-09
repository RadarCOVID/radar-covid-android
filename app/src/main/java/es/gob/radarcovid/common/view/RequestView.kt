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

interface RequestView {

    fun showLoading()

    fun hideLoading()

    fun hideLoadingWithError(error: Throwable)

    fun hideLoadingWithError(
        title: String?,
        message: String?,
        button: String,
        onClick: (() -> Unit) = {}
    )

    fun showError(error: Throwable, finishOnDismiss: Boolean = false)

}